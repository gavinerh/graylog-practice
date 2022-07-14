import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import './App.css';
import { NavigationBar } from './component/NavigationBar';
import ClassifyLogs from './component/ClassifyLogs';
import SideNav from './component/SideNav';
import ModifyKafkaTopics from './component/ModifyKafkaTopics';
import ModifyUdpStreaming from './component/ModifyUdpStreaming';
import EndpointList from './component/EndpointList';

function App() {
  return (
    <React.Fragment>
      <BrowserRouter>
        <NavigationBar />
        <SideNav/>
        <Switch>
          <Route exact path="/" component={EndpointList} />
          <Route path="/classification" component={ClassifyLogs} />
          <Route path="/modifyKafkaTopics" component={ModifyKafkaTopics} />
          <Route path="/modifyUdpStreaming" component={ModifyUdpStreaming} />
        </Switch>
      </BrowserRouter>
    </React.Fragment>
  );
}

export default App;
