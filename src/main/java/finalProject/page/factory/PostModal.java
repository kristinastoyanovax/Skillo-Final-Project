package finalProject.page.factory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PostModal {
    private final WebDriver driver;
    @FindBy(css = ".post-modal-img img")
    private WebElement image;
    @FindBy(className = "post-title")
    private WebElement postTitle;
    @FindBy(className = "post-user")
    private WebElement postUser;
    @FindBy(tagName = "app-post-modal")
    private WebElement modalElement;
    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/app-post-modal/div/div[2]/div[4]/div/div/div/div[4]/div/label/a")
    private WebElement deletePostButton;
    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/app-post-modal/div/div[2]/div[4]/div/div/div/div[4]/div/div/button[1]")
    private WebElement yesButton;



    public PostModal(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isImageVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.visibilityOf(image)).isDisplayed();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getPostTitle() {
        return postTitle.getText();
    }

    public String getPostUser() {
        return postUser.getText();
    }

    public void deletePost() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(deletePostButton));
        deletePostButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(yesButton));
        yesButton.click();

    }

}
