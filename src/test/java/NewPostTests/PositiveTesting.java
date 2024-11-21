
package NewPostTests;


import finalProject.page.factory.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

public class PositiveTesting extends TestObject {
    @DataProvider(name = "getDataWithImage")
    public Object[][] getDataWithImage() {
        File postPicture = new File("src/main/resources/uploads/owl.jpg");
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

        // gets number of posts
        profilePage.setNumberPosts(profilePage.getPostCount("public"));

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
        Assert.assertEquals(profilePage.getPostCount("public"), profilePage.expectedNumberPosts, "The number of Posts is incorrect!");

        // checks if the post is correct
        profilePage.clickPost();
        PostModal postModal = new PostModal(driver);
        Assert.assertTrue(postModal.isImageVisible(), "The image is not visible!");
        Assert.assertEquals(postModal.getPostTitle(), caption);
        Assert.assertEquals(postModal.getPostUser(), username);
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

        // gets number of posts
        profilePage.setNumberPosts(profilePage.getPostCount("private"));

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
        Assert.assertEquals(profilePage.getPostCount("private"), profilePage.expectedNumberPosts, "The number of Posts is incorrect!");

        // checks if the post is correct
        profilePage.clickPost();
        PostModal postModal = new PostModal(driver);
        Assert.assertTrue(postModal.isImageVisible(), "The image is not visible!");
        Assert.assertEquals(postModal.getPostTitle(), caption);
        Assert.assertEquals(postModal.getPostUser(), username);
    }
}
