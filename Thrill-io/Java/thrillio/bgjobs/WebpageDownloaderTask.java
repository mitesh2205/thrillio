package thrillio.bgjobs;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import thrillio.dao.BookmarkDao;
import thrillio.entities.WebLink;
import thrillio.util.HttpConnect;
import thrillio.util.IOUtil;

public class WebpageDownloaderTask implements Runnable {
	
	private static BookmarkDao dao = new BookmarkDao();
	
	private static final long TIME_FRAME = 3000000000L; //3 seconds
	
	private boolean downloadAll = false;
	
	ExecutorService downloadExecutor = Executors.newFixedThreadPool(5);
	
	//Static nested-class
	private static class Downloader<T extends WebLink> implements Callable<T> {
		private T weblink;
		
		public Downloader(T weblink) {
			this.weblink = weblink;
		}
		
		public T call() {
			try {
				if(!weblink.getUrl().endsWith(".pdf")) {
					weblink.setDownloadStatus(WebLink.DownloadStatus.FAILED);
					String htmlPage = HttpConnect.download(weblink.getUrl());
					weblink.setHtmlPage(htmlPage);
				}
				
				else {
					weblink.setDownloadStatus(WebLink.DownloadStatus.NOT_ELIGIBLE);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
			return weblink;
		}
	}
	
	public WebpageDownloaderTask(boolean downloadAll) {
		this.downloadAll = downloadAll;
	}

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			
			//Get web links
			List<WebLink> webLinks = getWebLinks();
			
			//Download web links concurrently
			if(webLinks.size() > 0) {
				download(webLinks);
			}
			
			else {
				System.out.println("No new web links to download.");
			}
			
			//Periodically downloads/tries to download web links every 15 seconds
			try {
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		downloadExecutor.shutdown();
	}

	private void download(List<WebLink> webLinks) {
		
		List<Downloader<WebLink>> tasks = getTasks(webLinks);
		List<Future<WebLink>> futures = new ArrayList<>();
		
		try {
			//invokeAll() method automatically cancels tasks
			futures = downloadExecutor.invokeAll(tasks, TIME_FRAME, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(Future<WebLink> future : futures) {
			try {
				
				//get() method will NOT block because the task has completed successfully
				if(!future.isCancelled()) {
					WebLink webLink = future.get();
					String webPage = webLink.getHtmlPage();
					
					//Web page has been downloaded successfully
					if(webPage != null) {
						IOUtil.write(webPage, webLink.getId()); //Write the webpage to the disk
						webLink.setDownloadStatus(WebLink.DownloadStatus.SUCCESS);
						System.out.println("The webpage downloaded successfully: " + webLink.getUrl());
					}
					
					else {
						System.out.println("The webpage was not downloaded: " + webLink.getUrl());
					}
				}
				
				//Task is cancelled
				else {
					System.out.println("\n\nTask was cancelled --> " + Thread.currentThread());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	private List<Downloader<WebLink>> getTasks(List<WebLink> webLinks) {
		List<Downloader<WebLink>> tasks = new ArrayList<>();
		
		for(WebLink webLink : webLinks) {
			tasks.add(new Downloader<WebLink>(webLink));
		}
		
		return tasks;
	}

	private List<WebLink> getWebLinks() {
		List<WebLink> webLinks = null;
		
		if(downloadAll) {
			webLinks = dao.getAllWebLinks();
			downloadAll = false;
		}
		
		else {
			webLinks = dao.getWebLinks(WebLink.DownloadStatus.NOT_ATTEMPTED);
		}
		
		return webLinks;
	}
}
