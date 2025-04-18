package thrillio;

import java.sql.SQLException;
import java.util.List;

import thrillio.constants.KidFriendlyStatus;
import thrillio.constants.UserType;
import thrillio.controllers.BookmarkController;
import thrillio.entities.Bookmark;
import thrillio.entities.User;
import thrillio.partner.Shareable;

//Represents the UI
public class View {
	
	public static void browse(User user, List<List<Bookmark>> bookmarks) throws SQLException
	{
		System.out.println("\n" + user.getEmail() +" is browsing items ...");
		
		int bookmarkCount = 0;
		
		//Iterates through each of the 3 bookmark types (i.e. web-links, movies, books)
		for(List<Bookmark> bookmarkList: bookmarks)
		{
			//Item level : Individual bookmarks within a specific type of bookmark
			for(Bookmark bookmark: bookmarkList)
			{
				
				boolean isBookmarked = getBookmarkDecision(bookmark);
				
				if(isBookmarked)
				{
					bookmarkCount++;
					System.out.println("New Item Bookmarked -- " + bookmark);
				}
				
				
				if(user.getUserType().equals(UserType.EDITOR)
						||user.getUserType().equals(UserType.CHIEF_EDITOR))
				{
					//Mark as kid-friendly
					if(bookmark.isKidFriendlyEligible() && bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN))
					{
						KidFriendlyStatus kidFriendlyStatus = getKidFriendlyStatusDecision(bookmark);
						
						if(!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN))
						{
							//BookmarkController.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);
							
						}
					}
					
					//Sharing; if bookmark is kid-friendly
					if(bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED) 
							&& bookmark instanceof Shareable)
					{
						boolean isShared = getShareDecision();
						if(isShared)
						{
							//BookmarkController.getInstance().share(user, bookmark);
						}
					}
				}
				
				
			}
		}
		
	}

	
	private static boolean getShareDecision() {
		return Math.random() < 0.5 ? true : false;
		
	}

	private static KidFriendlyStatus getKidFriendlyStatusDecision(Bookmark bookmark) {
		double decision = Math.random();
		
		return decision < 0.4 ? KidFriendlyStatus.APPROVED
				: (decision >= 0.4 && decision < 0.8) ? KidFriendlyStatus.REJECTED
						: KidFriendlyStatus.UNKNOWN;
	}

	private static boolean getBookmarkDecision(Bookmark bookmark) {
		return Math.random() < 0.5 ? true : false;
		
	}
	
	
}
