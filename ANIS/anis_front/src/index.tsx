import React, {StrictMode} from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter} from "react-router-dom";
import {CssBaseline, ThemeProvider} from "@mui/material";
import theme from './themes'
import {Provider} from "react-redux";
import store from "./redux/store";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {ReactQueryDevtools} from "@tanstack/react-query-devtools";

const queryClient = new QueryClient();

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <StrictMode>
        <ThemeProvider theme={theme}>
            <CssBaseline>
                <BrowserRouter>
                    <Provider store={store}>
                        <QueryClientProvider client={queryClient}>
                            <App />
                            <ReactQueryDevtools initialIsOpen={false} />
                        </QueryClientProvider>
                    </Provider>
                </BrowserRouter>
            </CssBaseline>
        </ThemeProvider>
    </StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
