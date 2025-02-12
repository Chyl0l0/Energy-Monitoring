import React from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Button,
    Card,
    CardHeader,
    Col,
    Modal,
    ModalBody,
    ModalHeader,
    Row
} from 'reactstrap';
// import DeviceDeleteForm from "./components/device-delete-form";

import * as API_USERS from "./api/device-api"
import DeviceTable from "./components/device-table";
// import DeviceUpdateForm from "./components/device-update-from";
import Cookies from "js-cookie";
import ChartContainer from "./chart-container";
import SockJsClient from 'react-stomp';
import { ToastContainer, toast } from 'react-toastify';

class UserContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.toggleDeleteForm = this.toggleDeleteForm.bind(this)
        this.toggleUpdateForm = this.toggleUpdateForm.bind(this)

        this.reload = this.reload.bind(this);
        this.state = {
            selected: false,
            collapseForm: false,
            tableData: [],
            isLoaded: false,
            errorStatus: 0,
            error: null,
            selectedDelete: false,
            selectedUpdate: false

        };
    }

    componentDidMount() {
        this.fetchDevices();
    }


    fetchDevices() {// returns the URL query String
        //const params = new URLSearchParams(window.location.search);

        return API_USERS.getDeviceByAccountId(Cookies.get('id'),(result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    tableData: result,
                    isLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    toggleForm() {
        this.setState({selected: !this.state.selected});
    }
    toggleDeleteForm() {
        this.setState({ selectedDelete: !this.state.selectedDelete});
    }
    toggleUpdateForm() {
        this.setState({ selectedUpdate: !this.state.selectedUpdate});
    }



    reload() {
        this.setState({
            isLoaded: false
        });
        this.setState({selected: false});
        this.setState({ selectedDelete: false});
        this.setState({ selectedUpdate: false});


        this.fetchDevices();
    }

    render() {
        return (
            <div>
                <CardHeader>
                    <strong> User Management </strong>
                </CardHeader>
                <Card>
                <div>
                    <SockJsClient url='http://localhost:8080/websocket' topics={['/topics/all']}
                                  onMessage={(msg) => { toast("Device " + msg["device_id"] + " consumed " + msg["measurement_value"]); console.log(msg) }}
                                  ref={ (client) => { this.clientRef = client }} />
                </div>
                </Card>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <DeviceTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                                            errorStatus={this.state.errorStatus}
                                                            error={this.state.error}
                                                        />   }
                        </Col>
                    </Row>
                    <Row>
                    </Row>

                </Card>
                <ChartContainer></ChartContainer>

                <ToastContainer />



            </div>
        )

    }
}


export default UserContainer;
