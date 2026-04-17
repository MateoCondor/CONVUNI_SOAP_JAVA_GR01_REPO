import { DefaultTheme, ThemeProvider } from '@react-navigation/native';
import { Stack } from 'expo-router';
import { StatusBar } from 'expo-status-bar';
import 'react-native-reanimated';
import { MD3LightTheme, PaperProvider } from 'react-native-paper';

export const unstable_settings = {
  anchor: '(tabs)',
};

const pastelPaperTheme = {
  ...MD3LightTheme,
  colors: {
    ...MD3LightTheme.colors,
    primary: '#5aa9e6',
    onPrimary: '#0b2a3f',
    primaryContainer: '#dcefff',
    onPrimaryContainer: '#14344c',
    secondary: '#83c5e8',
    background: '#edf7ff',
    surface: '#ffffff',
    surfaceVariant: '#e9f5ff',
    onSurface: '#17324a',
    onSurfaceVariant: '#45647a',
    outline: '#bfdced',
  },
};

const pastelNavTheme = {
  ...DefaultTheme,
  colors: {
    ...DefaultTheme.colors,
    primary: '#5aa9e6',
    background: '#edf7ff',
    card: '#ffffff',
    text: '#17324a',
    border: '#cfe5f4',
    notification: '#7fb9de',
  },
};

export default function RootLayout() {
  return (
    <PaperProvider theme={pastelPaperTheme}>
      <ThemeProvider value={pastelNavTheme}>
        <Stack>
          <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
          <Stack.Screen name="modal" options={{ presentation: 'modal', title: 'Modal' }} />
        </Stack>
        <StatusBar style="dark" />
      </ThemeProvider>
    </PaperProvider>
  );
}
