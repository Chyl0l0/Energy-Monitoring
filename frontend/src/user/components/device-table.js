import React from "react";
import Table from "../../commons/tables/table";


const columns = [
    {
        Header: 'Id',
        accessor: 'id',
    },
    {
        Header: 'Description',
        accessor: 'description',
    },
    {
        Header: 'Address',
        accessor: 'address',
    },
    {
        Header: 'Max hourly consuption',
        accessor: 'max_consumption',
    },
    {
        Header: 'Account',
        id: 'account',
        accessor: (data) => (data.account ? data.account.id : "")
    }
];

const filters = [
    {
        accessor: 'name',
    }
];

class DeviceTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tableData: this.props.tableData
        };
    }

    render() {
        console.log(this.state.tableData)
        return (
            <Table
                data={this.state.tableData}
                columns={columns}
                search={filters}
                pageSize={5}
            />
        )
    }
}

export default DeviceTable;