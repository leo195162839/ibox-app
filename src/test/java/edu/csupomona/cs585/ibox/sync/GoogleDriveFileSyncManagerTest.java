/*
 * CS585 Assignment 1
 * 
 * This is a unit testing class for the GoogleDriveFileSyncManager 
 * class, by using junit and Mockito.
 * 
 * @Author: Yuancheng Li*\
 */

package edu.csupomona.cs585.ibox.sync;

import static org.mockito.Mockito.*;

import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.Delete;
import com.google.api.services.drive.Drive.Files.Insert;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.Drive.Files.Update;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class GoogleDriveFileSyncManagerTest {
	
	//private variables
	private Drive mockDrive;
	private GoogleDriveFileSyncManager mockSyncManager;
	private java.io.File dummyFile;
	private String dummyName;
	private String googleId;
	private File googleFile;
	private File updatedFile;
	private Files mockFiles;
	private List mockList;
	private FileList googleList;
	private java.util.List<File> dummyList;


	/*
	 * 	precondition for all class*\
	 */
	@Before 
	public void setup(){
		mockDrive = mock(Drive.class);
		dummyFile = mock(java.io.File.class);	
		mockFiles = mock(Files.class);
		mockList = mock(List.class);
		mockSyncManager = new GoogleDriveFileSyncManager(mockDrive);
		dummyName = "testFile";
		googleId = "testfile";
		googleFile = new File();
		updatedFile = new File();
		googleFile.setTitle(dummyName);
		googleFile.setId(googleId);
		googleList = new FileList();
		dummyList = new ArrayList<File>();
		
		dummyList.add(googleFile);
		googleList.setItems(dummyList);
	}
	
	/*
	 * 	testing addFile method*\
	 */
	@Test
	public void addFileTest() throws IOException{
		//Setup
		googleFile = new File();
		
		//Mocking
		Insert mockInsert = mock(Insert.class);

		//Stubbing
		when(mockDrive.files()).thenReturn(mockFiles);
		when(mockFiles.insert(isA(File.class), isA(AbstractInputStreamContent.class))).thenReturn(mockInsert);
		when(mockInsert.execute()).thenReturn(googleFile);
		
		mockSyncManager.addFile(dummyFile);
		
		//Verify
		verify(mockDrive,atLeast(1)).files();
		verify(mockFiles,atLeast(1)).insert(isA(File.class), isA(AbstractInputStreamContent.class));
		verify(mockInsert,atLeast(1)).execute();
		
	}

	/*
	 * 	testing updateFile method*\
	 */
	@Test
 	public void updateFileTest() throws IOException{		
		//Mocking
		Update mockUpdate = mock(Update.class);		

		//Stubbing
		when(dummyFile.getName()).thenReturn(dummyName);		
		when(mockDrive.files()).thenReturn(mockFiles);
		when(mockFiles.list()).thenReturn(mockList);
		when(mockList.execute()).thenReturn(googleList);
		when(mockFiles.update(isA(String.class), isA(File.class), isA(AbstractInputStreamContent.class))).thenReturn(mockUpdate);
		when(mockUpdate.execute()).thenReturn(updatedFile);
		
		mockSyncManager.updateFile(dummyFile);
		
		//Verify
		verify(dummyFile,atLeast(1)).getName();
		verify(mockDrive,atLeast(1)).files();
		verify(mockFiles,atLeast(1)).list();
		verify(mockList,atLeast(1)).execute();
		verify(mockFiles,atLeast(1)).update(isA(String.class), isA(File.class), isA(AbstractInputStreamContent.class));
		verify(mockUpdate,atLeast(1)).execute();
	}

	/*
	 * 	Testing deleteFile method*\
	 */
	@Test
	public void deleteFileTest () throws IOException {		
		//Mocking
		Delete mockDelete = mock(Delete.class);
		
		//Stubbing
		when(dummyFile.getName()).thenReturn(dummyName);		
		when(mockDrive.files()).thenReturn(mockFiles);
		when(mockFiles.list()).thenReturn(mockList);
		when(mockList.execute()).thenReturn(googleList);
		when(mockFiles.delete(isA(String.class))).thenReturn(mockDelete);
		when(mockDelete.execute()).thenReturn(null);
		
		mockSyncManager.deleteFile(dummyFile);
		
		//Verify
		verify(dummyFile,atLeast(1)).getName();
		verify(mockDrive,atLeast(1)).files();
		verify(mockFiles,atLeast(1)).list();
		verify(mockList,atLeast(1)).execute();
		verify(mockFiles,atLeast(1)).delete(isA(String.class));
		verify(mockDelete,atLeast(1)).execute();
	}
}
