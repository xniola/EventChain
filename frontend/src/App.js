import React from "react";

import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";

import NavBar from './components/NavBar';
import FAQ from "./components/TicketBuyer/FAQ";
import Welcome from "./components/Welcome";
import HomePage from "./components/HomePage";

import CreaEvento from "./components/EventManager/CreaEvento";
import EventResellerList from "./components/EventManager/EventResellerList";
import TicketList from "./components/EventManager/TicketList";
import EventManagerEventi from "./components/EventManager/EventManagerEventi";
import ResellerRequest from "./components/TicketReseller/ResellerRequest";
import TicketResellerEventi from "./components/TicketReseller/TicketResellerEventi";
import PurchaseRequests from "./components/TicketReseller/PurchaseRequests";
import TicketBuyerEventi from "./components/TicketBuyer/TicketBuyerEventi";
import BuyerPurchaseRequests from "./components/TicketBuyer/BuyerPurchaseRequests";
import EventResellers from "./components/TicketBuyer/EventResellers";
import EliminaEvento from "./components/EventManager/EliminaEvento";
import CloseEvent from "./components/EventManager/CloseEvent";


const App = () => {
  return (
    <Router>
      <div className="App">
        <NavBar />
        <div className="content">
          <Switch>

            <Route exact path="/">
              <Welcome />
            </Route>

            <Route exact path="/user">
              <HomePage />
            </Route>

            <Route exact path="/create_event">
              <CreaEvento />
            </Route>
            

            {/*Si accede poi al campo :id tramite this.props.match.params.id */}
            <Route exact path="/event/:id/comments" component={FAQ}/>
            
            <Route exact path="/crea_evento">
              <CreaEvento />
            </Route>
            
            <Route exact path="/event/:id/findNewEventReseller" component={EventResellerList}/>

            <Route exact path="/event/:id/tickets" component={TicketList}/>

            <Route exact path="/event/:id/createEventReseller" component={ResellerRequest}/>

            <Route exact path="/eventManager/events" component={EventManagerEventi}/>

            <Route exact path="/ticketReseller/events" component={TicketResellerEventi}/>

            <Route excact path="/ticketBuyer/events" component={TicketBuyerEventi}/>

            <Route exact path = "/event/:id/resellerPurchaseRequests" component={PurchaseRequests}/>

            <Route exact path = "/event/:id/purchaseRequests" component={BuyerPurchaseRequests}/>

            <Route exact path = "/event/:id/resellers" component={EventResellers}/>

            <Route exact path = "/event/:id/comments" component={FAQ}/>

            <Route exact path = "/event/:id/delete" component={EliminaEvento}/>

            <Route exact path = "/event/:id/close" component={CloseEvent}/>

            </Switch>
        </div>
      </div>
    </Router>
  );
}

export default App;
