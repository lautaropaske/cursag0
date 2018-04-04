<%--
  Created by IntelliJ IDEA.
  User: agustin
  Date: 3/29/18
  Time: 6:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Login/Signup</title>
</head>
<body>

<section class="container">

    <h3 class="display-3 text-center mb-4">Welcome to Cursago!</h3>
    <div class="row">
        <div class="col-md-6">
            <form action="/j_security_check" method="POST">
                <h1>Log In</h1>
                    <div class="form-group">
                        <label for="inputEmail">Email</label>
                        <input type="email" class="form-control" id="inputEmail" name="j_username" placeholder="Email">
                    </div>
                    <div class="form-group">
                        <label for="inputPassword">Password</label>
                        <input type="password" class="form-control" id="inputPassword" name="j_password" placeholder="Password">
                    </div>
                <button type="submit" class="btn btn-primary">Log In</button>
            </form>
        </div>
        <div class="col-md-6">
            <form action="/create_account" method="POST">
                <h1>Sign Up</h1>
                <div class="form-group">
                    <label for="inputName">First Name</label>
                    <input type="text" class="form-control" name="name" id="inputName" placeholder="Name">
                </div>
                <div class="form-group">
                    <label for="inputSurname">Last Name</label>
                    <input type="text" class="form-control" name="surname" id="inputSurname" placeholder="Surname">
                </div>
                <div class="form-group">
                    <label for="inputEmail2">Email</label>
                    <input type="email" class="form-control" name="email" id="inputEmail2" placeholder="Email">
                </div>
                <div class="form-group">
                    <label for="inputPassword2">Password</label>
                    <input type="password" class="form-control" name="pass" id="inputPassword2" placeholder="Password">
                </div>
                <button type="submit" class="btn btn-primary">Create Account</button>
            </form>
        </div>
    </div>

</section>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</body>
</html>
