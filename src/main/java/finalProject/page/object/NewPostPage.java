//Create Public post with image and description
package finalProject.page.object;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class NewPostPage {
    public static final String PAGE_URL = "http://training.skillo-bg.com:4300/posts/create";
    private final WebDriver driver;

    public NewPostPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isUrlLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.urlToBe(NewPostPage.PAGE_URL));
    }

//    public String postAPictureText() {
//        WebElement newPostFormTitle = driver.findElement(By.className("h3"));
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOf(newPostFormTitle));
//        return newPostFormTitle.getText();
//    }

    public void clickCreatePost() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement createPostButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("create-post")));
        createPostButton.click();

    }

    public boolean isImageVisible() {
        try {
            WebElement image = driver.findElement(By.cssSelector("img.image-preview"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.visibilityOf(image)).isDisplayed();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getImageName() {
        WebElement imageTextElement = driver.findElement(By.cssSelector("input.input-lg"));
        return imageTextElement.getAttribute("placeholder");
    }

    public void uploadPicture(File file) {
        WebElement uploadField = driver.findElement(By.cssSelector(".file[type='file']"));
        uploadField.sendKeys(file.getAbsolutePath());
    }

    public void populatePostCaption(String caption) {
        WebElement captionElement = driver.findElement(By.name("caption"));
        captionElement.sendKeys(caption);
    }

    public void clickPostStatusToggle() {
        WebElement postStatus = driver.findElement(By.xpath("/html/body/app-root/div[2]/app-create-post/div/div/div/form/div[2]/div[3]/span/label[2]"));
        postStatus.click();
    }

    public boolean isStatusPublic() {
        WebElement postStatus = driver.findElement(By.cssSelector("label.active"));
        return postStatus.getText().equals("Public");
    }

}
