import React from 'react';
import validate from "./validators/account-validators";
import Button from "react-bootstrap/Button";
import * as API_USERS from "../api/account-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {Col, Row, Card} from "reactstrap";
import { FormGroup, Input, Label} from 'reactstrap';
import {Redirect} from "react-router-dom";
import Cookies from 'js-cookie'


class AccountLoginForm extends React.Component {


    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        //this.reloadHandler = this.props.reloadHandler;

        this.state = {

            errorStatus: 0,
            error: null,

            formIsValid: false,

            formControls: {
                username: {
                    value: '',
                    placeholder: 'User...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 3,
                        isRequired: true
                    }
                },
                password: {
                    value: '',
                    placeholder: 'Password...',
                    valid: false,
                    touched: false,
                    validationRules: {
                        minLength: 3,
                        isRequired: true
                    }
                },
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    toggleForm() {
        this.setState({collapseForm: !this.state.collapseForm});
    }


    handleChange = event => {

        const name = event.target.name;
        const value = event.target.value;

        const updatedControls = this.state.formControls;

        const updatedFormElement = updatedControls[name];

        updatedFormElement.value = value;
        updatedFormElement.touched = true;
        updatedFormElement.valid = validate(value, updatedFormElement.validationRules);
        updatedControls[name] = updatedFormElement;

        let formIsValid = true;
        for (let updatedFormElementName in updatedControls) {
            formIsValid = updatedControls[updatedFormElementName].valid && formIsValid;
        }

        this.setState({
            formControls: updatedControls,
            formIsValid: formIsValid
        });

    };

    loginAccount(account) {
        return API_USERS.login(account, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully logged in with id: " + result.id);
                //this.reloadHandler();
                Cookies.set('id', result.id)
                Cookies.set('role', result.role)
                if (result.role === 'admin')
                    window.location.href='/account'
                else
                    window.location.href='/user'




            }
            else if( status === 403){
                console.log(status);
            }
            else {

                this.setState(({
                    errorStatus: status,
                    error: error
                }));


            }
        });
    }

    handleSubmit() {
        let account = {
            username: this.state.formControls.username.value,
            password: this.state.formControls.password.value
        };

        console.log(account);
        this.loginAccount(account);
    }

    render() {
        return (

            <div>
                <Card>
                    <Row><Col sm={{size: '4', offset: 3}}>
                <FormGroup id='username'>
                    <Label for='usernameField'> Username: </Label>
                    <Input name='username' id='usernameField' placeholder={this.state.formControls.username.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.username.value}
                           touched={this.state.formControls.username.touched? 1 : 0}
                           valid={this.state.formControls.username.valid}
                           required
                    />
                    {this.state.formControls.username.touched && !this.state.formControls.username.valid &&
                        <div className={"error-message"}> * Email must have a valid format</div>}
                </FormGroup>
                    </Col></Row >
                    <Row><Col sm={{size: '4', offset: 3}}>
                <FormGroup id='password'>
                    <Label for='passwordField'> Password: </Label>
                    <Input name='password' id='passwordField' placeholder={this.state.formControls.password.placeholder}
                           type={"password"}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.password.value}
                           touched={this.state.formControls.password.touched? 1 : 0}
                           valid={this.state.formControls.password.valid}
                           required
                    />
                </FormGroup>
                    </Col>
                    </Row>
                <Row>
                    <Col sm={{size: '4', offset: 6}}>
                        <Button type={"submit"} disabled={!this.state.formIsValid} onClick={this.handleSubmit}>  Submit </Button>
                    </Col>
                </Row>
                </Card>
                {
                    this.state.errorStatus > 0 &&
                    <APIResponseErrorMessage errorStatus={this.state.errorStatus} error={this.state.error}/>
                }
            </div>
        ) ;
    }
}

export default AccountLoginForm;
