/*
 * CS585 Assignment 1
 * 
 * This is a integration testing class for the GoogleDriveFileSyncManager 
 * class, by using junit.
 * 
 * @Author: Yuancheng Li*\
 */

package edu.csupomona.cs585.ibox.sync;

import java.io.File;
import java.io.IOException;

import org.junit.*;

import com.google.api.services.drive.Drive;

public class GoogleDriveFileSyncManagerIntegrationTest {
	
	//private variables
	private Drive drive;
	private GoogleDriveFileSyncManager syncManager;
	private File dummyFile;
	private String dummyId;
	private String dummyName;
	
	/*
	 * 	precondition for all class*\
	 */
	@Before
	public void setup () {
		System.out.println("... ...");
		drive = GoogleDriveServiceProvider.get().getGoogleDriveClient();
		syncManager = new GoogleDriveFileSyncManager(drive);
		dummyName = "dummyFile.txt";
		dummyFile = new File("/Users/YuanchengLi/Documents/workspace/dummyFile.txt");
		System.out.println("Integration test initialized.");
		System.out.println();
	}
	
	/*
	 * 	Integration test for addFile method*\
	 */
	@Test
	public void addFileIntegrationTest() throws IOException {
		System.out.println("Start adding File.");
		System.out.println("... ...");
		syncManager.addFile(dummyFile);
		dummyId = syncManager.getFileId(dummyName);
		Assert.assertNotNull(dummyId);
		System.out.println("File added.");
		System.out.println();
	}
	
	/*
	 * 	Integration test for updateFile method*\
	 */
	@Test
	public void updateFileIntegrationTest() throws IOException {
		System.out.println("Start updating File.");
		System.out.println("... ...");
		syncManager.updateFile(dummyFile);
		dummyId = syncManager.getFileId(dummyName);
		Assert.assertNotNull(dummyId);
		System.out.println("File updated.");
		System.out.println();
	}
	
	/*
	 * 	Integration test for deleteFile method*\
	 */
	@Test
	public void deleteFileIntegrationTest() throws IOException {
		System.out.println("Start deleting File.");
		System.out.println("... ...");
		syncManager.deleteFile(dummyFile);
		dummyId = syncManager.getFileId(dummyName);
		Assert.assertNotNull(dummyId);
		System.out.println("File deleted.");
		System.out.println();
	}

}
