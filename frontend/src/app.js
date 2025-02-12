import React from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import NavigationBar from './navigation-bar'
import Home from './home/home';

import ErrorPage from './commons/errorhandling/error-page';
import styles from './commons/styles/project-style.css';
import AccountContainer from "./account/account-container";
import DeviceContainer from "./device/device-container";
import AccountLoginForm from "./account/components/account-login";
import UserContainer from "./user/user-container";
import ChartContainer from "./user/chart-container";
import Chat from "./chat/chat";
import Cookies from "js-cookie";
class App extends React.Component {


    render() {

        return (
            <div className={styles.back}>
            <Router>
                <div>
                    <NavigationBar />
                    <Switch>

                        <Route
                            exact
                            path='/'
                            render={() => <Home/>}
                        />

                        { Cookies.get('role') === 'admin'&&
                            <Route
                                exact
                                path='/account'
                                render={() => <AccountContainer/>}
                            />
                        }
                        {Cookies.get('role') === 'admin' &&

                            <Route
                                exact
                                path='/device'
                                render={() => <DeviceContainer/>}
                            />
                        }
                        <Route
                            exact
                            path='/login'
                            render={() => <AccountLoginForm/>}
                        />
                        {Cookies.get('id') &&
                            <Route
                                exact
                                path='/user'
                                render={() => <UserContainer/>}
                            />
                        }
                        {Cookies.get('id') &&

                            <Route
                            exact
                            path='/chart'
                            render={() => <ChartContainer/>}
                        />
                        }

                        {Cookies.get('id') &&

                        <Route
                        exact
                        path='/chat'
                        render={() => <Chat/>}
                        />
                        }
                        {/*Error*/}
                        <Route
                            exact
                            path='/error'
                            render={() => <ErrorPage/>}
                        />

                        <Route render={() =><ErrorPage/>} />
                    </Switch>
                </div>
            </Router>
            </div>
        )
    };
}

export default App
