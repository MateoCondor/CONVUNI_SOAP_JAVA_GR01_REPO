import { useState } from 'react';
import {
  KeyboardAvoidingView,
  Platform,
  StyleSheet,
  View,
  useWindowDimensions,
} from 'react-native';
import { router } from 'expo-router';
import { ActivityIndicator, Button, Card, Chip, Surface, Text, TextInput } from 'react-native-paper';

import {
  API_BASE_URL,
  clearSession,
  isSessionAuthenticated,
  login,
  setSessionAuthenticated,
} from '@/services/conversion-api';

export default function HomeScreen() {
  const { width } = useWindowDimensions();
  const isDesktop = width >= 900;

  const [username, setUsername] = useState('MONSTER');
  const [password, setPassword] = useState('MONSTER9');
  const [statusMessage, setStatusMessage] = useState('Ingrese credenciales para continuar.');
  const [loading, setLoading] = useState(false);

  const handleLogin = async () => {
    try {
      setLoading(true);
      const response = await login(username, password);

      if (!response.success) {
        clearSession();
        setStatusMessage(response.message);
        return;
      }

      setSessionAuthenticated(true);
      setStatusMessage(response.message);
      router.replace('/convert');
    } catch (error) {
      clearSession();
      setStatusMessage(error instanceof Error ? error.message : 'Login failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <KeyboardAvoidingView
      style={styles.screen}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}>
      <View style={[styles.container, isDesktop && styles.containerDesktop]}>
        <Text variant="displaySmall" style={styles.title}>
          {isDesktop ? 'CLIWEB' : 'CLIMOV'}
        </Text>
        <Text variant="titleMedium" style={styles.subtitle}>
          Iniciar sesión
        </Text>
        <Text variant="bodySmall" style={styles.urlText}>
          Backend URL: {API_BASE_URL}
        </Text>

        <Card mode="elevated" style={styles.card}>
          <Card.Content style={styles.cardContent}>
            <Text variant="titleMedium" style={styles.cardTitle}>
              Acceso requerido
            </Text>
          <TextInput
            mode="outlined"
            label="Username"
            autoCapitalize="none"
            value={username}
            onChangeText={setUsername}
          />
          <TextInput
            mode="outlined"
            label="Password"
            secureTextEntry
            value={password}
            onChangeText={setPassword}
          />
            <Button mode="contained" onPress={handleLogin} loading={loading} disabled={loading}>
              Ingresar
            </Button>
            <Chip
              icon={isSessionAuthenticated() ? 'check-circle-outline' : 'alert-circle-outline'}
              style={isSessionAuthenticated() ? styles.okChip : styles.warnChip}
              textStyle={isSessionAuthenticated() ? styles.okText : styles.warnText}>
              Estado: {isSessionAuthenticated() ? 'Sesión iniciada' : 'Sin sesión'}
            </Chip>
          </Card.Content>
        </Card>

        <Surface style={styles.statusBox} elevation={1}>
          {loading ? <ActivityIndicator color="#05668d" /> : null}
          <Text variant="bodyMedium" style={styles.statusText}>
            {statusMessage}
          </Text>
        </Surface>
      </View>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  screen: {
    flex: 1,
    backgroundColor: '#edf7ff',
  },
  container: {
    flex: 1,
    justifyContent: 'center',
    padding: 20,
    gap: 16,
    paddingBottom: 36,
  },
  containerDesktop: {
    alignSelf: 'center',
    width: '100%',
    maxWidth: 520,
  },
  title: {
    fontWeight: '700',
    color: '#174266',
  },
  subtitle: {
    color: '#2e5f83',
  },
  urlText: {
    color: '#4f7692',
  },
  card: {
    borderRadius: 16,
    backgroundColor: '#ffffff',
    borderWidth: 1,
    borderColor: '#d5e9f8',
  },
  cardContent: {
    paddingVertical: 6,
    gap: 10,
  },
  cardTitle: {
    fontWeight: '600',
    color: '#215878',
  },
  okChip: {
    backgroundColor: '#e0f4ff',
    alignSelf: 'flex-start',
  },
  warnChip: {
    backgroundColor: '#fff2dc',
    alignSelf: 'flex-start',
  },
  okText: {
    color: '#1f6e98',
    fontWeight: '600',
  },
  warnText: {
    color: '#9a6a2b',
    fontWeight: '600',
  },
  statusBox: {
    borderRadius: 12,
    padding: 12,
    gap: 8,
    backgroundColor: '#f8fcff',
    borderWidth: 1,
    borderColor: '#d7ebf9',
  },
  statusText: {
    color: '#355f7a',
  },
});
