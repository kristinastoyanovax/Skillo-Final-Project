package post;

import finalProject.page.object.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

public class PostTest extends TestObject{
    @DataProvider(name = "getDataWithImage")
    public Object[][] getDataWithImage() {
        File postPicture = new File("src/test/resources/uploads/owl.jpg");
        String caption = "Testing create post caption";

        return new Object[][]{{"krisiTest@gmail.com", "123456789K", "KristinaStoyanova", postPicture, caption}};
    }
    //1. Create Public post with image and description
    @Test(dataProvider = "getDataWithImage")
    public void publicPost (String email, String password, String username, File file, String caption) {
        //Gets a driver instance from parent class (NewPostTests.TestObject)
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        Header header = new Header(driver);
        header.clickLogin();


        //Log in
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isUrlLoaded(), "The Login URL is not correct!");
        String signInText = loginPage.getSignInElementText();
        Assert.assertEquals(signInText, "Sign in");
        loginPage.login(email, password);
        loginPage.clickSignIn();

        // navigates to profile
        Assert.assertTrue(homePage.isUrlLoaded(), "The Home URL is not correct!");
        header.clickProfile();

        // checks if the profile is correct
        ProfilePage profilePage = new ProfilePage(driver);
        Assert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");
        String actualUserName = profilePage.getUsername();
        Assert.assertEquals(actualUserName, username, "The username is incorrect!");

        // Creates post
        header.clickNewPost();
        NewPostPage postPage = new NewPostPage(driver);
        Assert.assertTrue(postPage.isUrlLoaded(), "The POST URL is not correct!");
        postPage.uploadPicture(file);
        Assert.assertTrue(postPage.isImageVisible(), "The image is not visible!");
        Assert.assertEquals(file.getName(), postPage.getImageName(), "The image name is incorrect!");
        postPage.populatePostCaption(caption);
        Assert.assertTrue(postPage.isStatusPublic(), "Default status isn't Public");
        postPage.clickCreatePost();

        // check if the number of posts is correct
        Assert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");

        // checks if the post is correct
        profilePage.clickPost();
        PostModal postModal = new PostModal(driver);
        Assert.assertTrue(postModal.isImageVisible(), "The image is not visible!");
        Assert.assertEquals(postModal.getPostTitle(), caption);
        Assert.assertEquals(postModal.getPostUser(), username);

        postModal.deletePost();
        // checks if there are 0 posts in the profile
        Assert.assertEquals(profilePage.getPostCount(), 0, "There shouldn't be any posts in the profile.");
    }

    //2. Create Private post with image and description
    @Test(dataProvider = "getDataWithImage")
    public void privatePost (String email, String password, String username, File file, String caption) {
        //Gets a driver instance from parent class (NewPostTests.TestObject)
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        Header header = new Header(driver);
        header.clickLogin();

        //Log in
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isUrlLoaded(), "The Login URL is not correct!");
        String signInText = loginPage.getSignInElementText();
        Assert.assertEquals(signInText, "Sign in");
        loginPage.login(email, password);
        loginPage.clickSignIn();


        // navigates to profile
        Assert.assertTrue(homePage.isUrlLoaded(), "The Home URL is not correct!");
        header.clickProfile();


        // checks if the profile is correct
        ProfilePage profilePage = new ProfilePage(driver);
        Assert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");
        String actualUserName = profilePage.getUsername();
        Assert.assertEquals(actualUserName, username, "The username is incorrect!");

        // Creates post
        header.clickNewPost();
        NewPostPage postPage = new NewPostPage(driver);
        Assert.assertTrue(postPage.isUrlLoaded(), "The POST URL is not correct!");
        postPage.uploadPicture(file);
        Assert.assertTrue(postPage.isImageVisible(), "The image is not visible!");
        Assert.assertEquals(file.getName(), postPage.getImageName(), "The image name is incorrect!");
        postPage.populatePostCaption(caption);
        Assert.assertTrue(postPage.isStatusPublic(), "Default status isn't Public");
        postPage.clickPostStatusToggle();
        Assert.assertFalse(postPage.isStatusPublic(), "Toggled status isn't Private");
        postPage.clickCreatePost();

        // check if the number of posts is correct
        Assert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");

        // checks if the post is correct
        profilePage.clickPost();
        PostModal postModal = new PostModal(driver);
        Assert.assertTrue(postModal.isImageVisible(), "The image is not visible!");
        Assert.assertEquals(postModal.getPostTitle(), caption);
        Assert.assertEquals(postModal.getPostUser(), username);

        postModal.deletePost();

        Assert.assertEquals(profilePage.getPostCount(), 0 , "There shouldn't be any posts in the profile.");
    }

    //1. Create Public post without image and with description
    @Test(dataProvider = "getDataWithImage")
    public void postWithoutImage(String email, String password, String username, File file, String caption) {
        //Gets a driver instance from parent class (NewPostTests.TestObject)
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        Header header = new Header(driver);
        header.clickLogin();


        //Log in
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isUrlLoaded(), "The Login URL is not correct!");
        String signInText = loginPage.getSignInElementText();
        Assert.assertEquals(signInText, "Sign in");
        loginPage.login(email, password);
        loginPage.clickSignIn();


        // navigates to profile
        Assert.assertTrue(homePage.isUrlLoaded(), "The Home URL is not correct!");
        header.clickProfile();


        // checks if the profile is correct
        ProfilePage profilePage = new ProfilePage(driver);
        Assert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");
        String actualUserName = profilePage.getUsername();
        Assert.assertEquals(actualUserName, username, "The username is incorrect!");


        // Creates post
        header.clickNewPost();
        NewPostPage postPage = new NewPostPage(driver);
        Assert.assertTrue(postPage.isUrlLoaded(), "The POST URL is not correct!");
        Assert.assertNotEquals(file.getName(), postPage.getImageName(), "The image name shouldn't match!");
        postPage.populatePostCaption(caption);
        Assert.assertTrue(postPage.isStatusPublic(), "Default status isn't Public");
        postPage.clickCreatePost();
        Assert.assertTrue(postPage.isUrlLoaded(), "The POST URL is not correct!");

    }

    //2. Create Private post with image and without description
    @Test(dataProvider = "getDataWithImage")
    public void postWithoutDescription(String email, String password, String username, File file, String caption) {
        //Gets a driver instance from parent class (NewPostTests.TestObject)
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        Header header = new Header(driver);
        header.clickLogin();


        //Log in
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isUrlLoaded(), "The Login URL is not correct!");
        String signInText = loginPage.getSignInElementText();
        Assert.assertEquals(signInText, "Sign in");
        loginPage.login(email, password);
        loginPage.clickSignIn();


        // navigates to profile
        Assert.assertTrue(homePage.isUrlLoaded(), "The Home URL is not correct!");
        header.clickProfile();


        // checks if the profile is correct
        ProfilePage profilePage = new ProfilePage(driver);
        Assert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");
        String actualUserName = profilePage.getUsername();
        Assert.assertEquals(actualUserName, username, "The username is incorrect!");


        // Creates post
        header.clickNewPost();
        NewPostPage postPage = new NewPostPage(driver);
        Assert.assertTrue(postPage.isUrlLoaded(), "The POST URL is not correct!");
        postPage.uploadPicture(file);
        Assert.assertTrue(postPage.isImageVisible(), "The image is not visible!");
        Assert.assertEquals(file.getName(), postPage.getImageName(), "The image name is incorrect!");
        Assert.assertTrue(postPage.isStatusPublic(), "Default status isn't Public");
        postPage.clickPostStatusToggle();
        Assert.assertFalse(postPage.isStatusPublic(), "Toggled status isn't Private");
        postPage.clickCreatePost();
        Assert.assertTrue(postPage.isUrlLoaded(), "The POST URL is not correct!");

    }

    //3. Create Public post with PDF file and description
    @DataProvider(name = "getDataWithPdf")
    public Object[][] getDataWithPdf() {
        File postPicture = new File("src/test/resources/uploads/file.pdf");
        String caption = "Testing create post caption";

        return new Object[][]{{"krisiTest@gmail.com", "123456789K", "KristinaStoyanova", postPicture, caption}};
    }
    @Test(dataProvider = "getDataWithPdf")
    public void postWithPdfFile(String email, String password, String username, File file, String caption) {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        Header header = new Header(driver);
        header.clickLogin();


        //Log in
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isUrlLoaded(), "The Login URL is not correct!");
        String signInText = loginPage.getSignInElementText();
        Assert.assertEquals(signInText, "Sign in");
        loginPage.login(email, password);
        loginPage.clickSignIn();


        // navigates to profile
        Assert.assertTrue(homePage.isUrlLoaded(), "The Home URL is not correct!");
        header.clickProfile();


        // checks if the profile is correct
        ProfilePage profilePage = new ProfilePage(driver);
        Assert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");
        String actualUserName = profilePage.getUsername();
        Assert.assertEquals(actualUserName, username, "The username is incorrect!");


        // Creates post
        header.clickNewPost();
        NewPostPage postPage = new NewPostPage(driver);
        Assert.assertTrue(postPage.isUrlLoaded(), "The POST URL is not correct!");
        postPage.uploadPicture(file);
        Assert.assertFalse(postPage.isImageVisible(), "The file should not be visible!");
        Assert.assertEquals(file.getName(), postPage.getImageName(), "The image name is incorrect!");
        postPage.populatePostCaption(caption);
        Assert.assertTrue(postPage.isStatusPublic(), "Default status isn't Public");
        postPage.clickCreatePost();
        Assert.assertTrue(postPage.isUrlLoaded(), "The POST URL is not correct!");

    }


}
