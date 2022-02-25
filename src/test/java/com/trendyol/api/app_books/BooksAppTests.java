package com.trendyol.api.app_books;

import com.trendyol.api.app_books.dto.BookDto;
import com.trendyol.api.app_books.exception.ApiError;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class BooksAppTests extends APITestCase {

    RequestSpecification rs = new RequestSpecBuilder()
            .setBaseUri(API_ROOT)
            .setContentType(ContentType.JSON)
            .setBasePath("/api/books")
            .build();
    RequestSpecification rsWithoutBasePath = new RequestSpecBuilder()
            .setBaseUri(API_ROOT)
            .setContentType(ContentType.JSON)
            .build();

    /**
     * For purposes clearing the database in the context
     * of final operations after each test. Took care about this
     */
    @AfterEach
    void teardownEndpoints() {
        given()
                .spec(rs)
                .when()
                .delete()
                .then()
                .statusCode(200);
    }

    @Test
    void checkAPIStartsWithEmptyStoreWhenGET() {
        List<String> list = given()
                .spec(rs)
                .when()
                .get()
                .then()
                .statusCode(200).extract().jsonPath().get();
        assertThat(list).isNotNull().isEmpty();
    }

    @Test
    void checkThatTitleRequiredFieldsAndCannotBeMissingDuringPUT() {
        BookDto newBook = BookDto.builder().author("William Shakespeare").build();
        ApiError apiError = given()
                .spec(rs)
                .body(newBook).when().put()
                .then().statusCode(400).extract().as(ApiError.class);
        assertThat(apiError).isNotNull();
        assertThat(apiError).extracting(ApiError::getError).isEqualTo("Field 'title' is required");
    }

    @Test
    void checkThatAuthorRequiredFieldsAndCannotBeMissingDuringPUT() {
        BookDto newBook = BookDto.builder().title("Rose").build();
        ApiError apiError = given()
                .spec(rs)
                .body(newBook).when().put()
                .then().statusCode(400).extract().as(ApiError.class);
        assertThat(apiError).isNotNull();
        assertThat(apiError).extracting(ApiError::getError).isEqualTo("Field 'author' is required");
    }

    @Test
    void checkThatTitleCannotBeEmptyDuringPUT() {
        BookDto newBook = BookDto.builder().author("John Williams").title("").build();
        ApiError apiError = given()
                .spec(rs)
                .body(newBook).when().put()
                .then().statusCode(400).extract().as(ApiError.class);
        assertThat(apiError).isNotNull();
        assertThat(apiError).extracting(ApiError::getError).isEqualTo("Field 'title' cannot be empty");
    }

    @Test
    void checkThatAuthorCannotBeEmptyDuringPUT() {
        BookDto newBook = BookDto.builder().author("").title("Mozart").build();
        ApiError apiError = given()
                .spec(rs)
                .body(newBook).when().put()
                .then().statusCode(400).extract().as(ApiError.class);
        assertThat(apiError).isNotNull();
        assertThat(apiError).extracting(ApiError::getError).isEqualTo("Field 'author' cannot be empty");
    }

    @Test
    void checkThatBookIdCannotSetBecauseGenerateByDefaultAfterPUT() {
        final String authName = "AuthorToCheckSetNotDefaultId";
        final String titleName = "TitleToCheckSetNotDefaultId";
        BookDto bookItem = BookDto.builder()
                .author(authName)
                .title(titleName)
                .id(3777000).build();
        BookDto createdBook = given()
                .spec(rs)
                .body(bookItem).when().put()
                .then().statusCode(200).extract().as(BookDto.class);
        assertThat(createdBook).isNotNull();
        assertThat(createdBook).extracting(BookDto::getAuthor).isEqualTo(authName);
        assertThat(createdBook).extracting(BookDto::getTitle).isEqualTo(titleName);
        assertThat(createdBook).extracting(BookDto::getId).isNotEqualTo(3777000);
    }

    @Test
    void checkThatWeCanCreateNewBookUsingPUTAndItReturnViaGET() {
        final String authName = "NewAuthorWillBeCreatedAndReturnedInResponse";
        final String titleName = "NewTitleWillBeCreatedAndReturnedInResponse";
        BookDto bookItem = BookDto.builder()
                .author(authName)
                .title(titleName)
                .build();
        BookDto createdBook = given()
                .spec(rs)
                .body(bookItem).when().put()
                .then().statusCode(200).extract().body().as(BookDto.class);
        assertThat(createdBook).extracting(BookDto::getAuthor).isEqualTo(authName);
        assertThat(createdBook).extracting(BookDto::getTitle).isEqualTo(titleName);

        long bookId = createdBook.getId();
        BookDto returnedBook = given()
                .spec(rsWithoutBasePath).basePath("/api/books/" + bookId)
                .when().get()
                .then().statusCode(200).extract().as(BookDto.class);
        assertThat(returnedBook).extracting(BookDto::getAuthor).isEqualTo(authName);
        assertThat(returnedBook).extracting(BookDto::getTitle).isEqualTo(titleName);
    }

    @Test
    void checkThatWeCannotCreateDuplicateBookViaPUT() {
        BookDto newBook = BookDto.builder()
                .author("AuthorToCheckSetDublicate")
                .title("TitleToCheckSetDublicate")
                .build();
        given()
                .spec(rs)
                .body(newBook).when().put()
                .then().statusCode(200).extract().body().as(BookDto.class);
        ApiError apiError = given()
                .spec(rs)
                .body(newBook).when().put()
                .then().statusCode(409).extract().as(ApiError.class);
        assertThat(apiError).isNotNull();
        assertThat(apiError).extracting(ApiError::getError).isEqualTo("Another book with similar title and author already exists");
    }

    /**
     * require1 At the beginning of a test case, there should be no books stored on the server.
     * But nothing said about in the middle or at the end, so we can expect some books in store
     * not at the beginning
     */
    @Test
    void makeSureAtLeastOneTestDetectsRegression() {
        List<String> list = given()
                .spec(rs)
                .when()
                .get()
                .then()
                .statusCode(200).extract().jsonPath().get();
        assertThat(list).isNotNull().isNotEmpty();
    }
}
