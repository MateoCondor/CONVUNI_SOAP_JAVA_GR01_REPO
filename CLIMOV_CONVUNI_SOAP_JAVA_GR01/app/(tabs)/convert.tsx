import { router } from 'expo-router';
import { useEffect, useMemo, useState } from 'react';
import {
  KeyboardAvoidingView,
  Platform,
  ScrollView,
  StyleSheet,
  View,
  useWindowDimensions,
} from 'react-native';
import {
  ActivityIndicator,
  Button,
  Card,
  Menu,
  SegmentedButtons,
  Surface,
  Text,
  TextInput,
} from 'react-native-paper';

import {
  API_BASE_URL,
  clearSession,
  convert,
  ConversionCategory,
  ConversionResponse,
  isSessionAuthenticated,
} from '@/services/conversion-api';

const unitsByCategory: Record<ConversionCategory, string[]> = {
  length: ['mm', 'cm', 'm', 'km', 'in', 'ft', 'yd', 'mi'],
  mass: ['mg', 'g', 'kg', 'lb', 'oz', 't'],
  temperature: ['c', 'f', 'k', 'r', 're'],
};

const categoryLabels: Record<ConversionCategory, string> = {
  length: 'Longitud',
  mass: 'Masa',
  temperature: 'Temperatura',
};

export default function ConvertScreen() {
  const { width } = useWindowDimensions();
  const isDesktop = width >= 960;

  const [category, setCategory] = useState<ConversionCategory>('length');
  const [value, setValue] = useState('1');
  const [fromUnit, setFromUnit] = useState('m');
  const [toUnit, setToUnit] = useState('cm');
  const [loading, setLoading] = useState(false);
  const [statusMessage, setStatusMessage] = useState('Seleccione las unidades y convierta.');
  const [result, setResult] = useState<ConversionResponse | null>(null);
  const [fromMenuVisible, setFromMenuVisible] = useState(false);
  const [toMenuVisible, setToMenuVisible] = useState(false);

  const availableUnits = useMemo(() => unitsByCategory[category], [category]);

  useEffect(() => {
    if (!isSessionAuthenticated()) {
      router.replace('/');
    }
  }, []);

  useEffect(() => {
    const defaults = unitsByCategory[category];
    setFromUnit(defaults[0]);
    setToUnit(defaults[1] ?? defaults[0]);
  }, [category]);

  const handleConvert = async () => {
    const numericValue = Number(value);

    if (Number.isNaN(numericValue)) {
      setStatusMessage('El valor debe ser numérico.');
      return;
    }

    try {
      setLoading(true);
      const response = await convert(category, numericValue, fromUnit, toUnit);
      setResult(response);
      setStatusMessage(response.message);
    } catch (error) {
      setStatusMessage(error instanceof Error ? error.message : 'No se pudo convertir.');
      setResult(null);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    clearSession();
    router.replace('/');
  };

  const formatUnit = (unit: string) => unit.toUpperCase();

  return (
    <KeyboardAvoidingView
      style={styles.screen}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}>
      <ScrollView contentContainerStyle={styles.container}>
        <View style={styles.headerRow}>
          <View>
            <Text variant="headlineMedium" style={styles.title}>
              Conversión de unidades
            </Text>
            <Text variant="bodySmall" style={styles.subtitle}>
              Backend URL: {API_BASE_URL}
            </Text>
          </View>
          <Button
            mode="outlined"
            icon="logout"
            buttonColor="#e6f3ff"
            textColor="#2a6486"
            style={styles.logoutButton}
            onPress={handleLogout}>
            Cerrar sesión
          </Button>
        </View>

        <View style={[styles.grid, isDesktop && styles.gridDesktop]}>
          <Card mode="elevated" style={[styles.card, isDesktop && styles.cardWide]}>
            <Card.Content style={styles.cardContent}>
              <Text variant="titleMedium" style={styles.cardTitle}>
                Parámetros
              </Text>

              <Text variant="labelMedium" style={styles.label}>
                Tipo de conversión
              </Text>
              <SegmentedButtons
                value={category}
                onValueChange={(nextValue) => setCategory(nextValue as ConversionCategory)}
                buttons={[
                  { value: 'length', label: 'Longitud' },
                  { value: 'mass', label: 'Masa' },
                  { value: 'temperature', label: 'Temp.' },
                ]}
              />

              <TextInput
                mode="outlined"
                label="Valor"
                placeholder="Ej: 10"
                keyboardType="decimal-pad"
                value={value}
                onChangeText={setValue}
              />

              <View style={styles.row}>
                <View style={styles.halfField}>
                  <Text variant="labelMedium" style={styles.label}>
                    Desde
                  </Text>
                  <Menu
                    visible={fromMenuVisible}
                    onDismiss={() => setFromMenuVisible(false)}
                    anchor={
                      <Button mode="outlined" onPress={() => setFromMenuVisible(true)}>
                        {formatUnit(fromUnit)}
                      </Button>
                    }>
                    {availableUnits.map((unit) => (
                      <Menu.Item
                        key={`from-${unit}`}
                        title={formatUnit(unit)}
                        onPress={() => {
                          setFromUnit(unit);
                          setFromMenuVisible(false);
                        }}
                      />
                    ))}
                  </Menu>
                </View>

                <View style={styles.halfField}>
                  <Text variant="labelMedium" style={styles.label}>
                    Hacia
                  </Text>
                  <Menu
                    visible={toMenuVisible}
                    onDismiss={() => setToMenuVisible(false)}
                    anchor={
                      <Button mode="outlined" onPress={() => setToMenuVisible(true)}>
                        {formatUnit(toUnit)}
                      </Button>
                    }>
                    {availableUnits.map((unit) => (
                      <Menu.Item
                        key={`to-${unit}`}
                        title={formatUnit(unit)}
                        onPress={() => {
                          setToUnit(unit);
                          setToMenuVisible(false);
                        }}
                      />
                    ))}
                  </Menu>
                </View>
              </View>

              <Button mode="contained" onPress={handleConvert} loading={loading} disabled={loading}>
                Convertir
              </Button>
            </Card.Content>
          </Card>

          <Card mode="elevated" style={styles.card}>
            <Card.Content style={styles.cardContent}>
              <Text variant="titleMedium" style={styles.cardTitle}>
                Resultado
              </Text>
              {result ? (
                <Surface style={styles.resultBox} elevation={0}>
                  <Text variant="bodyMedium" style={styles.resultLine}>
                    Categoría: {categoryLabels[result.category as ConversionCategory] ?? result.category}
                  </Text>
                  <Text variant="bodyLarge" style={styles.resultLine}>
                    {result.inputValue} {result.fromUnit} = {result.convertedValue} {result.toUnit}
                  </Text>
                </Surface>
              ) : (
                <Text variant="bodyMedium" style={styles.placeholder}>
                  Aún no hay resultado.
                </Text>
              )}

              <Surface style={styles.statusBox} elevation={0}>
              {loading ? <ActivityIndicator color="#005f73" /> : null}
                <Text variant="bodyMedium" style={styles.statusText}>
                  {statusMessage}
                </Text>
              </Surface>
            </Card.Content>
          </Card>
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  screen: {
    flex: 1,
    backgroundColor: '#edf7ff',
  },
  container: {
    paddingHorizontal: 16,
    paddingVertical: 20,
    gap: 14,
    width: '100%',
    maxWidth: 1100,
    alignSelf: 'center',
  },
  headerRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    gap: 10,
    flexWrap: 'wrap',
  },
  title: {
    fontSize: 28,
    fontWeight: '800',
    color: '#174266',
  },
  subtitle: {
    fontSize: 12,
    color: '#4a7290',
  },
  logoutButton: {
    borderWidth: 1,
    borderColor: '#b6d9ef',
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 8,
    backgroundColor: '#e6f3ff',
  },
  grid: {
    gap: 14,
  },
  gridDesktop: {
    flexDirection: 'row',
    alignItems: 'stretch',
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
  cardWide: {
    flex: 1.2,
  },
  cardTitle: {
    fontWeight: '700',
    color: '#215878',
  },
  label: {
    fontWeight: '600',
    color: '#4a6f8b',
  },
  row: {
    flexDirection: 'row',
    gap: 10,
  },
  halfField: {
    flex: 1,
    gap: 6,
  },
  resultBox: {
    borderRadius: 10,
    padding: 12,
    backgroundColor: '#f2f9ff',
    gap: 6,
    borderWidth: 1,
    borderColor: '#d7ebf9',
  },
  resultLine: {
    color: '#1f4f74',
    fontWeight: '600',
  },
  placeholder: {
    color: '#6484a0',
  },
  statusBox: {
    marginTop: 8,
    borderRadius: 10,
    padding: 12,
    backgroundColor: '#f7fcff',
    gap: 8,
    borderWidth: 1,
    borderColor: '#d7ebf9',
  },
  statusText: {
    color: '#355f7a',
  },
});
