package com.angebhd.studentManagement.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.angebhd.studentManagement.DTO.UserData;

/**
 * Core OTP service methods for MFA implementation
 */

public class OtpService {
    
    // In-memory storage for OTPs with user identifiers as keys
    // In production, consider using Redis or another distributed cache
    private final Map<String, OtpData> otpStorage = new ConcurrentHashMap<>();
    
    // Configurable parameters
    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 2;
    private static final int MAX_VERIFICATION_ATTEMPTS = 3;
    
    private final EmailService emailService;
    
    public OtpService(EmailService emailService) {
        this.emailService = emailService;
    }
    
    /**
     * Generates a new OTP for a user and sends it via email
     * @param userId User identifier (email or ID)
     * @param userData User data for email personalization
     * @return true if OTP was generated and sent successfully
     */
    public boolean generateAndSendOtp(String userId, UserData userData) {
        // Generate a random 6-digit OTP
        String otp = generateOtp(OTP_LENGTH);
        
        // Store OTP with expiration time
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);
        otpStorage.put(userId, new OtpData(otp, expiryTime));
        
        // Send OTP email
        return sendOtpEmail(userId, otp, userData);
    }
    
    /**
     * Validates an OTP provided by the user
     * @param userId User identifier
     * @param userProvidedOtp OTP provided by the user
     * @return OtpValidationResult indicating validation status
     */
    public OtpValidationResult validateOtp(String userId, String userProvidedOtp) {
        OtpData storedOtpData = otpStorage.get(userId);
        
        // Check if OTP exists for this user
        if (storedOtpData == null) {
            return OtpValidationResult.NO_OTP_FOUND;
        }
        
        // Check if OTP is expired
        if (LocalDateTime.now().isAfter(storedOtpData.expiryTime)) {
            // Remove expired OTP
            otpStorage.remove(userId);
            return OtpValidationResult.EXPIRED;
        }
        
        // Increment attempt counter
        storedOtpData.incrementAttempt();
        
        // Check if max attempts exceeded
        if (storedOtpData.attempts > MAX_VERIFICATION_ATTEMPTS) {
            otpStorage.remove(userId);
            return OtpValidationResult.MAX_ATTEMPTS_EXCEEDED;
        }
        
        // Validate OTP
        if (storedOtpData.otp.equals(userProvidedOtp)) {
            // OTP validated successfully, remove it to prevent reuse
            otpStorage.remove(userId);
            return OtpValidationResult.SUCCESS;
        }
        
        return OtpValidationResult.INVALID;
    }
    
    /**
     * Generates a random numeric OTP of specified length
     */
    private String generateOtp(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10)); // Append digit between 0-9
        }
        
        return otp.toString();
    }
    
    /**
     * Sends OTP to the user via email
     */
    private boolean sendOtpEmail(String email, String otp, UserData userData) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        int year = LocalDateTime.now().getYear();
        
        String fullName = userData.getFirstName() + " " + userData.getLastName();
        
        String htmlBody = """
                <!DOCTYPE html>
                <html>
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>Your Authentication Code</title>
                </head>
                <body style="font-family: 'Segoe UI', Arial, sans-serif; line-height: 1.6; max-width: 600px; margin: 0 auto; padding: 20px; color: #333333;">
                  <div style="background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); overflow: hidden;">
                    <!-- Header -->
                    <div style="background-color: #0066cc; color: white; padding: 20px; text-align: center;">
                      <h1 style="margin: 0; font-size: 24px;">Authentication Code</h1>
                    </div>
                    
                    <!-- Content -->
                    <div style="padding: 30px 20px;">
                      <p style="font-size: 16px;">Hello %s,</p>
                      
                      <p style="font-size: 16px;">
                        To complete your login to the Student Management System, please use the following verification code:
                      </p>
                      
                      <!-- OTP Box -->
                      <div style="background-color: #f5f5f5; border-radius: 5px; padding: 20px; text-align: center; margin: 25px 0;">
                        <p style="font-size: 32px; font-weight: bold; letter-spacing: 5px; margin: 0; color: #0066cc; font-family: monospace;">%s</p>
                      </div>
                      
                      <p style="font-size: 16px;">This code will expire in <strong>2 minutes</strong>.</p>
                      
                      <p style="margin-top: 25px; font-size: 14px;">
                        If you did not request this code, please ignore this email and consider changing your password.
                      </p>
                    </div>
                    
                    <!-- Security Note -->
                    <div style="background-color: #f9f9f9; padding: 15px 20px; border-top: 1px solid #eeeeee;">
                      <p style="margin: 0; font-size: 14px; color: #666666;">
                        <strong>Security Tip:</strong> Never share this code with anyone, including staff members or support.
                      </p>
                    </div>
                    
                    <!-- Footer -->
                    <div style="background-color: #f0f0f0; padding: 15px; text-align: center; font-size: 12px; color: #666666;">
                      <p style="margin: 0;">
                        Student Management System &copy; %d Ange Buhendwa
                      </p>
                      <p style="margin: 5px 0 0;">
                        <a href="#" style="color: #0066cc; text-decoration: none;">Privacy Policy</a> |
                        <a href="#" style="color: #0066cc; text-decoration: none;">Contact Support</a>
                      </p>
                    </div>
                  </div>
                </body>
                </html>
                """.formatted(fullName, otp, year);
        
        try {
            emailService.sendHtmlEmail(email, "Your Authentication Code - Student Management System", htmlBody);
            return true;
        } catch (Exception e) {
            // Log the exception
            return false;
        }
    }
    
    /**
     * Simple inner class to store OTP data with expiration time and attempt counter
     */
    private static class OtpData {
        private final String otp;
        private final LocalDateTime expiryTime;
        private int attempts;
        
        public OtpData(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
            this.attempts = 0;
        }
        
        public void incrementAttempt() {
            this.attempts++;
        }
    }
    
    /**
     * Enum representing possible OTP validation results
     */
    public enum OtpValidationResult {
        SUCCESS,
        INVALID,
        EXPIRED,
        NO_OTP_FOUND,
        MAX_ATTEMPTS_EXCEEDED
    }
}