post("/login", [
        email: "test@test.com",
        password: "123456"
    ]
)
.then(200, [
        token: "jwtToken"
])
.catch(400, [
    email: "Must not be empty",
    password: "Must not be empty",
    generic : "Invalid email or password"
])
.catch(500 , [
        generic: "Something went wrong, please try again"
])