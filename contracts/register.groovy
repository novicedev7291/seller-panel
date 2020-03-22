post("/register", [
        companyName: "Test pvt. ltd",
        firstName: "Marcus",
        lastName: "Wahun",
        email: "test@test.com",
        confirmPassword: "123456",
        password: "123456",
        phone: "8328329209",
        countryCode: "+01",
        roleId: null
])
.then(201, [
        token: "jwtToken"
])
.then(400, [
        email: "Must not be empty",
        generic: "Invalid message or something"
])
.then(500, [
        generic: "Something went wrong, please try again"
])