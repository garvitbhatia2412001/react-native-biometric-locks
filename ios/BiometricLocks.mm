#import "BiometricLocks.h"
#import <LocalAuthentication/LocalAuthentication.h>

@implementation BiometricLocks
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(openBiometricAuthenticatePrompt:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject)
{
    
    // Create a new instance of LAContext for biometric authentication
    LAContext *context = [[LAContext alloc] init];
    NSError *error = nil;

    // Check if the device supports biometric authentication
    if ([context canEvaluatePolicy:LAPolicyDeviceOwnerAuthentication error:&error]) {

        // Check if the available biometric on the device is Face ID
        if (context.biometryType == LABiometryTypeFaceID) {
            // Start the authentication process with Face ID
            [context evaluatePolicy:LAPolicyDeviceOwnerAuthentication
                    localizedReason:@"Authenticate with Face ID"
                              reply:^(BOOL success, NSError *error) {
                if (success) {
                    // Authentication successful
                    
                    NSString *biometricMessage = @"SUCCESS";
                        NSDictionary *result = @{
                          @"result": @(YES),
                          @"message": biometricMessage
                        };
                    
                    resolve(result);
                } else {
                    // Authentication failed
                    NSString *biometricMessage = @"FAILED";
                        NSDictionary *result = @{
                          @"result": @(NO),
                          @"message": biometricMessage
                        };
                    
                    resolve(result);
                }
            }];
        }
        else if(context.biometryType == LABiometryTypeTouchID){
          // Start the authentication process with Touch ID
          [context evaluatePolicy:LAPolicyDeviceOwnerAuthentication
                  localizedReason:@"Authenticate with Touch ID"
                            reply:^(BOOL success, NSError *error) {
              if (success) {
                  // Authentication successful
                  NSString *biometricMessage = @"SUCCESS";
                      NSDictionary *result = @{
                        @"result": @(YES),
                        @"message": biometricMessage
                      };
                  
                  resolve(result);
                } else {
                    // Authentication failed
                    NSString *biometricMessage = @"FAILED";
                        NSDictionary *result = @{
                          @"result": @(NO),
                          @"message": biometricMessage
                        };
                    
                    resolve(result);
                }
          }];
        }
        else {
          // Start the authentication process with Touch ID
          [context evaluatePolicy:LAPolicyDeviceOwnerAuthentication
                  localizedReason:@"Authenticate with Passcode"
                            reply:^(BOOL success, NSError *error) {
              if (success) {
                    // Authentication successful
                  NSString *biometricMessage = @"SUCCESS";
                      NSDictionary *result = @{
                        @"result": @(YES),
                        @"message": biometricMessage
                      };
                  
                  resolve(result);
                } else {
                    // Authentication failed
                    NSString *biometricMessage = @"FAILED";
                        NSDictionary *result = @{
                          @"result": @(NO),
                          @"message": biometricMessage
                        };
                    
                    resolve(result);
                }
          }];
        }
    } else {
        // Biometric authentication is not available on this device
        NSString *biometricMessage = @"Biometric authentication is not available on this device";
            NSDictionary *result = @{
              @"result": @(YES),
              @"message": biometricMessage
            };
        
        resolve(result);
    }
}


@end
