import { Children, use, useState } from "react";
import { createBrowserRouter } from "react-router";
import ReactDOM from "react-dom/client";
import React from "react";
import { RouterProvider } from "react-router-dom";
import CreatePollComponent from './Components/CreatePollComponent'
import { Outlet, Link } from "react-router-dom";
import { Poll } from "./Components/Poll";
import PollView from "./Components/PollView";


function CustomPoll():Poll  {
  
  const question = ""
  const poll = new Poll(question);
  
  return poll;
}


function App() {
  const [pageState, setPageState] = useState();

  return (
    <h1>hello</h1>
  )

}

export default App

