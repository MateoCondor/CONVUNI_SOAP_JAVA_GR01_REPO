import { Platform } from 'react-native';

export type ConversionCategory = 'length' | 'mass' | 'temperature';

export type LoginResponse = {
  success: boolean;
  message: string;
};

export type ConversionResponse = {
  success: boolean;
  message: string;
  category: string;
  inputValue: number;
  fromUnit: string;
  toUnit: string;
  convertedValue: number;
};

const defaultBaseUrl = Platform.select({
  android: 'http://192.168.100.158:8080/WS_CONVUNI_SOAP_JAVA_GR01/resources',
  default: 'http://localhost:8080/WS_CONVUNI_SOAP_JAVA_GR01/resources',
}) as string;

const envBaseUrl = process.env.EXPO_PUBLIC_API_BASE_URL?.trim();
const rawBaseUrl = envBaseUrl && envBaseUrl.length > 0 ? envBaseUrl : defaultBaseUrl;

export const API_BASE_URL = rawBaseUrl.endsWith('/') ? rawBaseUrl.slice(0, -1) : rawBaseUrl;

let authenticatedSession = false;

async function postJson<T>(path: string, body: Record<string, unknown>): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
    body: JSON.stringify(body),
  });

  const json = (await response.json()) as T;

  if (!response.ok) {
    throw new Error((json as { message?: string }).message ?? 'Request failed');
  }

  return json;
}

export function login(username: string, password: string) {
  return postJson<LoginResponse>('/api/login', { username, password });
}

export function convert(category: ConversionCategory, value: number, fromUnit: string, toUnit: string) {
  return postJson<ConversionResponse>(`/api/convert/${category}`, {
    value,
    fromUnit,
    toUnit,
  });
}

export function setSessionAuthenticated(value: boolean) {
  authenticatedSession = value;
}

export function isSessionAuthenticated() {
  return authenticatedSession;
}

export function clearSession() {
  authenticatedSession = false;
}
