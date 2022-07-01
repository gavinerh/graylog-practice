import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import './App.css';
import { NavigationBar } from './component/NavigationBar';
import ClassifyLogs from './component/ClassifyLogs';
import Dashboard from './component/Dashboard';
import SideNav from './component/SideNav';
import ModifyDashboard from './component/ModifyDashboard';

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
        </Switch>
      </BrowserRouter>
    </React.Fragment>
  );
}

export default App;
