package com.blog.blog_login_module.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.blog_login_module.dto.LoginRequest;
import com.blog.blog_login_module.dto.OtpRequest;
import com.blog.blog_login_module.dto.RegisterRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name="Authentication", description="All the user Authentication APIs")
@RequestMapping("api/v1/blog/login/auth")
public interface AuthEndpoint {

	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "New User Registration success"),
	@ApiResponse( responseCode = "500", description="Internal Server Error"),
	@ApiResponse( responseCode =  "400", description = "Bad Request")
})
	@Operation(summary = "User Register Endpoint", tags = { "Authentication","None" })
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request);
	
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "New User Login success"),
			@ApiResponse( responseCode = "500", description="Internal Server Error"),
			@ApiResponse( responseCode =  "400", description = "Bad Request")
		})
			@Operation(summary = "User Login Endpoint", tags = { "Authentication","Otp" })
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request);
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "User Otp Verification success"),
			@ApiResponse( responseCode = "500", description="Internal Server Error"),
			@ApiResponse( responseCode =  "400", description = "Bad Request")
		})
			@Operation(summary = "User VerifyOtp Endpoint", tags = { "Authentication","Home" })
	@PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request);
	
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "User resend otp success"),
			@ApiResponse( responseCode = "500", description="Internal Server Error"),
			@ApiResponse( responseCode =  "400", description = "Bad Request")
		})
			@Operation(summary = "User Resend Otp Endpoint", tags = { "Authentication","None" })
	@PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestParam String email);
	
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "User logout success"),
			@ApiResponse( responseCode = "500", description="Internal Server Error"),
			@ApiResponse( responseCode =  "400", description = "Bad Request")
		})
			@Operation(summary = "User Logout Endpoint", tags = { "Authentication","None" })
	@PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request);
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Get User success"),
			@ApiResponse( responseCode = "500", description="Internal Server Error"),
			@ApiResponse( responseCode =  "400", description = "Bad Request")
		})
			@Operation(summary = "User getUser", tags = { "Authentication","None" })
	@GetMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username);
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Get All User success"),
			@ApiResponse( responseCode = "500", description="Internal Server Error"),
			@ApiResponse( responseCode =  "400", description = "Bad Request")
		})
			@Operation(summary = "User getAllUser", tags = { "Authentication","None" })
	@GetMapping("/user")
    public ResponseEntity<?> getAllUsers();
}
