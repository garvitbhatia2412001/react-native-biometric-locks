import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';

import { openBiometricAuthenticatePrompt } from 'react-native-biometric-locks';

const colors = {
  "black": "#000",
  "white": "#fff"
}

export default function App() {
  const [response, setRsponse] = React.useState<Object | any>({});

  React.useEffect(() => {
    checkBiometricAuthenticationAndOpenPrompt()
  }, []);

  const checkBiometricAuthenticationAndOpenPrompt = async () => {
    await openBiometricAuthenticatePrompt().then((res) => {
      console.log(res);
      setRsponse(res);
    })
  }

  return (
    <View style={styles.container}>
      <Text style={styles.textColor}>Available:{response['result'] ? "true" : "false"}</Text>
      <Text style={styles.textColor}>Result:{response['message']}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: colors.white
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
  textColor: {
    color: colors.black
  }
});
