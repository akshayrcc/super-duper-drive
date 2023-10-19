package com.akshayram.cloudstorage;

import com.akshayram.cloudstorage.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/* tests for Notes */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTests extends CloudStorageApplicationTests {

  @Test
  public void testDelete() {
    String noteTitle = "Sample Note Title";
    String noteDescription = "Hellow World!!! This is sample note content.";
    /*Get homepage instance*/
    HomePage homePage = signUpAndLoginTest();

    /*create a new note for the given details*/
    createNote(noteTitle, noteDescription, homePage);

    homePage.navToNotesTab();
    homePage = new HomePage(driver);
    Assertions.assertFalse(homePage.noNotes(driver));
    deleteNote(homePage);
    Assertions.assertTrue(homePage.noNotes(driver));
  }

  private void deleteNote(HomePage homePage) {
    /*delete action to call on Home page*/
    homePage.deleteNote();
    ResultPage resultPage = new ResultPage(driver);
    resultPage.clickOk();
  }

  @Test
  public void testCreateAndDisplay() {
    String noteTitle = "Sample Note Title";
    String noteDescription = "Hellow World!!! This is sample note content.";
    /*Signup and Login into home page*/
    HomePage homePage = signUpAndLoginTest();
    /*create a new note*/
    createNote(noteTitle, noteDescription, homePage);
    homePage.navToNotesTab();
    homePage = new HomePage(driver);
    /*fetch the note*/
    Note note = homePage.getFirstNote();
    Assertions.assertEquals(noteTitle, note.getNoteTitle());
    Assertions.assertEquals(noteDescription, note.getNoteDescription());
    /*clear up*/
    deleteNote(homePage);
    homePage.logout();
  }

  @Test
  public void testModify() {
    String noteTitle = "Note 002";
    String noteDescription = "Hellow World!!! This is sample note content.";

    /*Signup and Login into home page*/
    HomePage homePage = signUpAndLoginTest();
    /*create a new note*/
    createNote(noteTitle, noteDescription, homePage);

    homePage.navToNotesTab();
    homePage = new HomePage(driver);

    /*edit the selected note*/
    homePage.editNote();

    String modifiedNoteTitle = "003";
    String modifiedNoteDescription = "Hellow World!!! This is modified sample note content.";

    /*modify the exising note title and desc*/
    homePage.modifyNoteTitle(modifiedNoteTitle);
    homePage.modifyNoteDescription(modifiedNoteDescription);
    /*save the changes*/
    homePage.saveNoteChanges();
    /*result page Ok*/
    ResultPage resultPage = new ResultPage(driver);
    resultPage.clickOk();

    homePage.navToNotesTab();

    /*Fetch back the same note*/
    Note note = homePage.getFirstNote();
    Assertions.assertEquals(modifiedNoteTitle, note.getNoteTitle());
    Assertions.assertEquals(modifiedNoteDescription, note.getNoteDescription());
  }

  private void createNote(String noteTitle, String noteDescription, HomePage homePage) {
    /*Steps performed while creating a new note*/
    /*home page actions*/
    homePage.navToNotesTab();
    homePage.addNewNote();
    homePage.setNoteTitle(noteTitle);
    homePage.setNoteDescription(noteDescription);
    homePage.saveNoteChanges();

    /*result page actions*/
    ResultPage resultPage = new ResultPage(driver);
    resultPage.clickOk();

    /*navigate back to Notes tab*/
    homePage.navToNotesTab();
  }
}
