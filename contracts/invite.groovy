get("/invite/{access_token_id}")
.then(200, [
        email: "test@test.com"
])
.catch(403, [
        generic: "Link expired, please join us again"
])
.catch(500, [
        generic: "Something went wrong, please try again"
])