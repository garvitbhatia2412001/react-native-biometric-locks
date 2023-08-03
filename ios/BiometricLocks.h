
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNBiometricLocksSpec.h"

@interface BiometricLocks : NSObject <NativeBiometricLocksSpec>
#else
#import <React/RCTBridgeModule.h>

@interface BiometricLocks : NSObject <RCTBridgeModule>
#endif

@end
