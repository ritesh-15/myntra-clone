import { createGlobalStyle } from "styled-components";

const GlobalStyle = createGlobalStyle`
    *{
        margin: 0;
        padding: 0;
        box-sizing: border-box;
   
    }

    h1 , h2 , h3 , h4 , h5 , h6, span, a , button, input, textarea, label{
        font-family: "Poppins", sans-serif;
        font-size: 1rem;
        font-weight: normal;
    }

    a {
        text-decoration: none;
        color: inherit;
    }

    input{
        font-family: "Poppins", sans-serif;
        font-size: 1rem;
        outline: none;
    }

    textarea{
        font-family: "Poppins", sans-serif;
        font-size: 1rem;
        outline: none;
    }

    ul , li{
        list-style: none;
    }

`;

export default GlobalStyle;
