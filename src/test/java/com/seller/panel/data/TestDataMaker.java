package com.seller.panel.data;

import com.github.javafaker.Faker;
import com.seller.panel.dto.Registration;
import com.seller.panel.dto.RegistrationRequest;
import com.seller.panel.model.Companies;
import com.seller.panel.model.Users;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import org.springframework.beans.BeanUtils;

public class TestDataMaker {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String PORT = "0";
    public static final String WRONG_EMAIL = "dummy-seller-panel1";
    public static final String EMAIL1 = "dummy-seller-panel1@gmail.com";
    public static final String EMAIL2 = "dummy-seller-panel2@gmail.com";
    public static final String JWT_SECRET = "test-jwt-secret";
    public static final String JWT_EXPIRATION = "604800";
    public static final String JWT_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkdW1teS1zZWxsZXItcGFuZWxAZ21haWwuY29tIiwiZXhwIjoxNTg0MTcwODA0LCJpYXQiOjE1ODQxNzE0MDgsImp0aSI6IjRiMjMxYzc2LTM3OWEtNDNkYi04MzdjLTMwMGNjYzQ2MDM4ZCJ9.g93PLrYweYawCX2FSVHL6m9upRHrIrqZBKfT8z3lNjVQUKfOCm8l5kaCDcICjqlZr7Yk-aCjqZ29TtQZ5PUy8w";
    public static final String INVITE_ID = "94333c51-c619-4664-b7c0-61179c930617";
    public static final String UI_INVITE_URL = "http://localhost"+PORT+ EndPointConstants.ENDPOINTS_PREFIX+"invite/{0}";
    public static final String PASSWORD = "Passw@rd";
    public static final String JOIN_TOKEN_EXPIRY = "7200000";
    public static final String REDIS_HOST = "localhost";
    public static final String REDIS_PORT = "6379";
    public static final String REDIS_PASSWORD = "redis_pass";

    public static Users makeUser() {
        Faker faker = new Faker();
        Users user = new Users();
        user.setId(faker.random().nextLong());
        user.setPassword(faker.internet().password());
        user.setActive(true);
        user.setEmail(faker.internet().emailAddress());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setPhone(faker.phoneNumber().cellPhone());
        user.setCountryCode(faker.country().countryCode2());
        Companies company = makeCompany();
        user.setCompany(company);
        user.setCompanyId(company.getId());
        return user;
    }

    public static Companies makeCompany() {
        Faker faker = new Faker();
        Companies company = new Companies();
        company.setId(faker.random().nextLong());
        company.setName(faker.company().name());
        company.setCode(faker.company().suffix());
        return company;
    }

    public static RegistrationRequest makeRegistrationRequestWithWrongEmail() {
        Faker faker = new Faker();
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail(faker.internet().domainName());
        request.setPassword(faker.internet().password());
        return request;
    }

    public static RegistrationRequest makeRegistrationRequestWithMismatchInPassword() {
        Faker faker = new Faker();
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName(faker.name().firstName());
        request.setLastName(faker.name().lastName());
        request.setPhone(faker.phoneNumber().cellPhone());
        request.setCountryCode(faker.country().countryCode2());
        request.setEmail(faker.internet().emailAddress());
        request.setCompanyName(faker.company().name());
        request.setPassword(AppConstants.VALID_PASSWORD);
        request.setConfirmPassword(faker.internet().password());
        return request;
    }

    public static RegistrationRequest makeRegistrationRequest() {
        Faker faker = new Faker();
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName(faker.name().firstName());
        request.setLastName(faker.name().lastName());
        request.setPhone(faker.phoneNumber().cellPhone());
        request.setCountryCode(faker.country().countryCode2());
        request.setEmail(faker.internet().emailAddress());
        request.setCompanyName(faker.company().name());
        request.setPassword(AppConstants.VALID_PASSWORD);
        request.setConfirmPassword(request.getPassword());
        return request;
    }

    public static Registration makeRegistration() {
        Registration request = new Registration();
        BeanUtils.copyProperties(makeRegistrationRequest(), request);
        return request;
    }

    public static Registration makeRegistrationWithMismatchInPassword() {
        Registration request = new Registration();
        BeanUtils.copyProperties(makeRegistrationRequestWithMismatchInPassword(), request);
        return request;
    }

}
