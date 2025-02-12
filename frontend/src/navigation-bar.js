import React from 'react'
import logo from './commons/images/icon.png';

import {
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Nav,
    Navbar,
    NavbarBrand,
    NavLink,
    UncontrolledDropdown
} from 'reactstrap';
import Cookies from "js-cookie";
const textStyle = {
    color: 'white',
    textDecoration: 'none'
};

const NavigationBar = () => (
    <div>
        <Navbar color="dark" light expand="md">
            <NavbarBrand href="/">
                <img src={logo} width={"50"}
                     height={"35"} />
            </NavbarBrand>
            <Nav className="mr-auto" navbar>
                { Cookies.get('role') === 'admin' &&

                <UncontrolledDropdown nav inNavbar>
                    <DropdownToggle style={textStyle} nav caret>
                       Menu
                    </DropdownToggle>
                        <DropdownMenu right>
                            <DropdownItem>
                                <NavLink href="/account">Accounts</NavLink>
                            </DropdownItem>
                            <DropdownItem>
                                <NavLink href="/device">Devices</NavLink>
                            </DropdownItem>


                        </DropdownMenu>
                </UncontrolledDropdown>
                }

                <NavLink style={textStyle} nav caret href="/login">Login</NavLink>
                {Cookies.get('id')  &&

                    <NavLink style={textStyle} nav caret href="/user">Profile</NavLink>
                }
            </Nav>
        </Navbar>
    </div>
);

export default NavigationBar
