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
import {AccountForm, AccountDeleteForm, AccountUpdateForm, AccountAddDeviceForm} from "./components/account-form";
// import AccountDeleteForm from "./components/account-delete-form";

import * as API_USERS from "./api/account-api"
import AccountTable from "./components/account-table";
// import AccountUpdateForm from "./components/account-update-from";



class AccountContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.toggleDeleteForm = this.toggleDeleteForm.bind(this)
        this.toggleUpdateForm = this.toggleUpdateForm.bind(this)
        this.toggleAddDeviceForm = this.toggleAddDeviceForm.bind(this)

        this.reload = this.reload.bind(this);
        this.state = {
            selected: false,
            collapseForm: false,
            tableData: [],
            isLoaded: false,
            errorStatus: 0,
            error: null,
            selectedDelete: false,
            selectedUpdate: false,
            selectedAddDevice: false


        };
    }

    componentDidMount() {
        this.fetchAccounts();
    }

    fetchAccounts() {
        return API_USERS.getAccounts((result, status, err) => {

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
    toggleAddDeviceForm() {
        this.setState({ selectedAddDevice: !this.state.selectedAddDevice});
    }



    reload() {
        this.setState({
            isLoaded: false
        });
        this.setState({selected: false});
        this.setState({ selectedDelete: false});
        this.setState({ selectedUpdate: false});
        this.setState({ selectedAddDevice: false});


        this.fetchAccounts();
    }

    render() {
        return (
            <div>
                <CardHeader>
                    <strong> Account Management </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '1', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm}>Add Account </Button>
                        </Col>
                        <Col sm={{size: '1', offset: 1}}>
                            <Button color="primary" onClick={this.toggleDeleteForm}>Delete Account </Button>
                        </Col>
                        <Col sm={{size: '1', offset: 1}}>
                            <Button color="primary" onClick={this.toggleUpdateForm}>Update Account </Button>
                        </Col>
                        <Col sm={{size: '1', offset: 1}}>
                            <Button color="primary" onClick={this.toggleAddDeviceForm}>Add Device to Account </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <AccountTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                                            errorStatus={this.state.errorStatus}
                                                            error={this.state.error}
                                                        />   }
                        </Col>
                    </Row>
                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add Account: </ModalHeader>
                    <ModalBody>
                        <AccountForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedDelete} toggle={this.toggleDeleteForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleDeleteForm}> Delete Account: </ModalHeader>
                    <ModalBody>
                        <AccountDeleteForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedUpdate} toggle={this.toggleUpdateForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleUpdateForm}> Update Account: </ModalHeader>
                    <ModalBody>
                        <AccountUpdateForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedAddDevice} toggle={this.toggleAddDeviceForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleAddDeviceForm}> Add Device to Account: </ModalHeader>
                    <ModalBody>
                        <AccountAddDeviceForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

            </div>
        )

    }
}


export default AccountContainer;
