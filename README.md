# react-native-biometric-locks

React Native module for biometric functionality for Touch ID, Face ID, Passcode and other biometrics. Supports both iOS and Android.

## Installation

```sh
npm install react-native-biometric-locks
```

## Usage

```js
import { openBiometricAuthenticatePrompt } from 'react-native-biometric-locks';

// ...

const result = await openBiometricAuthenticatePrompt((res)=>{
    // res - {"message": "SUCCESS", "result": true}
    setResult(result);
});

 /* 
 Response will be an object with two keys
 message - value
 result - value

 Response --> {"message": "SUCCESS", "result": true}

 message - Biometric message, Success, Failed, Biometrics available or not enrolled etc.
 result - It will tell that biometric authentication success or failed.

 Note ----> If there is no biometric enrolled than it will return true in result with Biometric features not
            enrolled in Android and Biometric authentication is not available on this device in iOS.
*/

```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
