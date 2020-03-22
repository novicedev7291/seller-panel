post("/join", [
        email: "test@test.com"
])
.then(201, [
        email: "test@test.com",
        generic: "successful"
])
.catch(400, [
        email : "Must not be empty or should be valid"
])
.catch(500, [
        generic: "Something went wrong"
])