package thrillio;

import java.sql.SQLException;
import java.util.List;

import thrillio.bgjobs.WebpageDownloaderTask;
import thrillio.entities.Bookmark;
import thrillio.entities.User;
import thrillio.managers.BookmarkManager;
import thrillio.managers.UserManager;

public class Launch {
	
	private static List<User> users;
	
	private static List<List<Bookmark>> bookmarks;
	
	private static void loadData() {
		System.out.println("1. Loading data ...");
		DataStore.loadData();
		
		users = UserManager.getInstance().getUsers();
		bookmarks = BookmarkManager.getInstance().getBookmarks();
		
	}

	private static void printUserData() {
		
		System.out.println("User Data");
		for(User user: users)
		{
			System.out.println(user);
		}
		
	}
	
	private static void printBookmarkData() {
		
		System.out.println("\nBookmark Data");
		for(List<Bookmark> bookmarkList: bookmarks)
		{
			for (Bookmark bookmark : bookmarkList) 
			{
				System.out.println(bookmark);
			}
		}
		
	}
	
	private static void start() throws SQLException {
		for(User user: users)
		{
			View.browse(user, bookmarks);
		}
		
	}
	
	private static void runDownloaderJob() {
		WebpageDownloaderTask task = new WebpageDownloaderTask(true);
		(new Thread(task)).start();
	}

	public static void main(String[] args) throws SQLException {
		loadData();
		System.out.println();
		
		//printUserData();
		//printBookmarkData();
		
		start();

		//Background jobs
		//runDownloaderJob();
	}

	
	

	

}
