package com.seller.panel.data;

import com.github.javafaker.Faker;
import com.seller.panel.dto.Registration;
import com.seller.panel.dto.RegistrationRequest;
import com.seller.panel.dto.UserRequest;
import com.seller.panel.model.Companies;
import com.seller.panel.model.Permissions;
import com.seller.panel.model.Roles;
import com.seller.panel.model.Users;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    public static final String WRONG_PASSWORD = "123456";
    public static final String JOIN_TOKEN_EXPIRY = "7200000";
    public static final String REDIS_HOST = "localhost";
    public static final String REDIS_PORT = "6379";
    public static final String REDIS_PASSWORD = "redis_pass";

    public static Users makeUsers() {
        Faker faker = new Faker();
        Users user = new Users();
        user.setId(faker.random().nextLong());
        user.setPassword("$2a$10$701uMTqsFG1kfL/ymQGd3.ii0j55jnnW6d5dkJLuEAB6SZ2UfyhfO");
        user.setActive(true);
        user.setEmail(faker.internet().emailAddress());
        user.setName(faker.name().name());
        user.setPhone(faker.phoneNumber().cellPhone());
        user.setCountryCode(faker.country().countryCode2());
        Companies company = makeCompany();
        user.setCompany(company);
        user.setCompanyId(company.getId());
        Set<Roles> roles = new HashSet<>();
        roles.add(makeRoles());
        roles.add(makeRoles());
        user.setRoles(roles);
        return user;
    }

    public static Roles makeRoles(){
        Faker faker = new Faker();
        Roles role = new Roles();
        role.setId(faker.random().nextLong());
        role.setName(faker.name().name());
        Set<Permissions> permissions = new HashSet<>();
        permissions.add(makePermissions());
        permissions.add(makePermissions());
        role.setPermissions(permissions);
        return role;
    }

    public static Permissions makePermissions(){
        Faker faker = new Faker();
        Permissions permission = new Permissions();
        permission.setId(faker.random().nextLong());
        permission.setName(faker.name().name());
        return permission;
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
        request.setName(faker.name().name());
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
        request.setName(faker.name().name());
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

    public static Map<String, Object> makeAdditionalInfo() {
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put(AppConstants.COMPANY_ID, NumberUtils.LONG_ONE);
        return additionalInfo;
    }

    public static SimpleMailMessage makeSimpleMailMessage() {
        Faker faker = new Faker();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(faker.internet().emailAddress());
        simpleMailMessage.setSubject(faker.name().name());
        simpleMailMessage.setText(simpleMailMessage.getSubject());
        return simpleMailMessage;
    }

    public static UserRequest makeUserRequestWithMismatchInPassword() {
        UserRequest request = new UserRequest();
        BeanUtils.copyProperties(makeRegistrationRequestWithMismatchInPassword(), request);
        return request;
    }

    public static UserRequest makeUserRequest() {
        UserRequest request = new UserRequest();
        BeanUtils.copyProperties(makeRegistrationRequest(), request);
        return request;
    }

    public static Cookie[] makeCookies() {
        String[] tokenPart = TestDataMaker.JWT_TOKEN.split("[.]");
        return new Cookie[]{new Cookie(AppConstants.HEADER_PAYLOAD,
                            tokenPart[0].concat(".").concat(tokenPart[1])),
                        new Cookie(AppConstants.SIGNATURE, tokenPart[2])};
    }

}
