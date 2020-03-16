package com.seller.panel.data;

import com.github.javafaker.Faker;
import com.seller.panel.model.Companies;
import com.seller.panel.model.Users;
import com.seller.panel.util.EndPointConstants;
import org.apache.commons.lang3.math.NumberUtils;

public class TestDataMaker {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String PORT = "0";
    public static final String WRONG_EMAIL = "dummy-seller-panel1";
    public static final String EMAIL1 = "dummy-seller-panel1@gmail.com";
    public static final String EMAIL2 = "dummy-seller-panel2@gmail.com";
    public static final String JWT_SECRET = "test-jwt-secret";
    public static final String JWT_EXPIRATION = "604800";
    public static final String JWT_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkdW1teS1zZWxsZXItcGFuZWxAZ21haWwuY29tIiwiZXhwIjoxNTg0MTcwODA0LCJpYXQiOjE1ODQxNzE0MDgsImp0aSI6IjRiMjMxYzc2LTM3OWEtNDNkYi04MzdjLTMwMGNjYzQ2MDM4ZCJ9.g93PLrYweYawCX2FSVHL6m9upRHrIrqZBKfT8z3lNjVQUKfOCm8l5kaCDcICjqlZr7Yk-aCjqZ29TtQZ5PUy8w";
    public static final String REGISTER_ID = "94333c51-c619-4664-b7c0-61179c930617";
    public static final String UI_REGISTER_URL = "http://localhost"+PORT+ EndPointConstants.ENDPOINTS_PREFIX+"register/{0}";
    public static final String PASSWORD = "Passw@rd";

    public static Users makeUser() {
        Faker faker = new Faker();
        Users user = new Users();
        user.setId(faker.random().nextLong());
        user.setPassword(faker.internet().password());
        user.setActive(true);
        user.setCompanyId(NumberUtils.LONG_ONE);
        user.setEmail(faker.internet().emailAddress());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setPhone(faker.phoneNumber().cellPhone());
        Companies company = new Companies();
        company.setId(user.getCompanyId());
        company.setName(faker.company().name());
        company.setCode(faker.company().suffix());
        user.setCompany(company);
        return user;
    }


}
