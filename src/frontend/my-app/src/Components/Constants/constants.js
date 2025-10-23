const isProd = import.meta.env.VITE_POLL_PROD;

export const BACKEND_URL = isProd
  ? 'https://magnus-demo-project.com/polling/backend'
  : 'http://localhost:8080/polling/backend';