get("/user/{id}")
.then(200, [
        id: 1,
        companyName: "Test pvt. ltd",
        firstName: "Marcus",
        lastName: "Wahun",
        email: "test@test.com",
        phone: "8328329209",
        countryCode: "+01",
        roleId: null
])
.catch()