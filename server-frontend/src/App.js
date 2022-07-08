import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import './App.css';
import { NavigationBar } from './component/NavigationBar';
import ClassifyLogs from './component/ClassifyLogs';
import Dashboard from './component/Dashboard';
import SideNav from './component/SideNav';
import ModifyDashboard from './component/ModifyDashboard';
import ModifyKafkaTopics from './component/ModifyKafkaTopics';
import ModifyUdpStreaming from './component/ModifyUdpStreaming';

function App() {
  return (
    <React.Fragment>
      <BrowserRouter>
        <NavigationBar />
        <SideNav/>
        <Switch>
          <Route exact path="/" component={Dashboard} />
          <Route path="/modifyDashboard" component={ModifyDashboard} />
          <Route path="/classification" component={ClassifyLogs} />
          <Route path="/modifyKafkaTopics" component={ModifyKafkaTopics} />
          <Route path="/modifyUdpStreaming" component={ModifyUdpStreaming} />
        </Switch>
      </BrowserRouter>
    </React.Fragment>
  );
}

export default App;
