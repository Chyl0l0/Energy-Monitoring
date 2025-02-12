import React, {useState} from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Button,
    Card,
    CardHeader,
    Col, Input, Label,
    Modal,
    ModalBody,
    ModalHeader,
    Row
} from 'reactstrap';
// import DeviceDeleteForm from "./components/device-delete-form";

import * as API_USERS from "./api/device-api"
;
// import DeviceUpdateForm from "./components/device-update-from";
import DatePicker from 'react-datepicker';

import "react-datepicker/dist/react-datepicker.css";
import { BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import moment from "moment";





class ChartContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.toggleDeleteForm = this.toggleDeleteForm.bind(this)
        this.toggleUpdateForm = this.toggleUpdateForm.bind(this)
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.reload = this.reload.bind(this);
        this.state = {
            selected: false,
            collapseForm: false,
            tableData: [],
            isLoaded: false,
            errorStatus: 0,
            error: null,
            value: "",
            selectedDelete: false,
            selectedUpdate: false,
            startDate: new Date()


        };


    }


    handleChange(date) {
        this.setState({
            startDate: date
        })
    }

    handleSubmit(){
        console.log(this.state.value)
        console.log(moment(this.state.startDate).format("DD-MM-YYYY"))

        this.fetchDevices(this.state.value)
    }

    componentDidMount() {
        // this.fetchDevices();
    }

    fetchDevices(id) {// returns the URL query String
        const params = new URLSearchParams(window.location.search);
        console.log(this.state.value)
        if(id === "")
            return ;
        return API_USERS.getEnergyById(id,(result, status, err) => {

            if (result !== null && status === 200) {
                result.forEach(d => {
                    d.timestamp = moment(d.timestamp).valueOf();
                });

                var selectedDate = moment(this.state.startDate).format("DD-MM-YYYY")
                result = result.filter(d => moment.utc(d.timestamp).format("DD-MM-YYYY") === ( selectedDate) )
                console.log(result)

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
    dateFormatter = date => {
        // return moment(date).unix();

        return moment.utc(date).format('HH mm');
    };

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
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '2', offset: 1}}>
                        <DatePicker
                            selected={ this.state.startDate }
                            onChange={ this.handleChange }
                            name="startDate"
                            dateFormat="MM/dd/yyyy"
                        />
                        </Col>
                        <Col sm={{size: '2', offset: 1}}>

                        <Input name='id' id='idField' placeholder= 'Device Id...'
                                onChange={e => this.setState({value:e.target.value})}
                                value = {this.state.value}
                               // defaultValue={this.state.formControls.id.value}
                               // touched={this.state.formControls.id.touched? 1 : 0}
                               // valid={this.state.formControls.id.valid}

                               required
                        />
                        </Col>

                        <Col sm={{size: '4', offset: 1}}>
                            <Button type={"submit"}  onClick={this.handleSubmit}>  Submit </Button>

                        </Col>
                    </Row>

                    <Row>
                        <Col sm={{size: '4', offset: 1}}>

                        <BarChart width={750} height={750} data={this.state.tableData}>
                            <CartesianGrid strokeDasharray="3 3" />
                            <XAxis dataKey="timestamp" tickFormatter={this.dateFormatter} />
                            <YAxis />
                            {/*<Tooltip />*/}
                            {/*<Legend />*/}
                            <Bar dataKey="energy_consumption" fill="#8884d8"  />
                        </BarChart>
                        </Col>
                    </Row>
                </Card>




            </div>
        )

    }
}


export default ChartContainer;
