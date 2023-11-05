import React from "react";
import { Box, Text, useDisclosure } from "@chakra-ui/react";

import ServiceList from "../components/services/ServiceList";
import ActivateModal from "../components/modals/ActivateModal";
import DeactivateModal from "../components/modals/DeactivateModal";

const myServices = [
	{
		id: 1,
		name: "Service 1",
		description: "This is a service 1",
		validTimeDuration: "1 Month",
		serviceCharge: 100,
	},
	{
		id: 2,
		name: "Service 2",
		description: "This is a service 2",
		validTimeDuration: "2 Month",
		serviceCharge: 200,
	},
	{
		id: 3,
		name: "Service 3",
		description: "This is a service 3",
		validTimeDuration: "3 Month",
		serviceCharge: 300,
	},
];

//all services

const allServices = [
	{
		id: 1,
		name: "Service 1",
		description: "This is a service 1",
		validTimeDuration: "1 Month",
		serviceCharge: 100,
	},
	{
		id: 2,
		name: "Service 2",
		description: "This is a service 2",
		validTimeDuration: "2 Month",
		serviceCharge: 200,
	},
	{
		id: 3,
		name: "Service 3",
		description: "This is a service 3",
		validTimeDuration: "3 Month",
		serviceCharge: 300,
	},
	{
		id: 4,
		name: "Service 4",
		description: "This is a service 4",
		validTimeDuration: "4 Month",
		serviceCharge: 400,
	},
	{
		id: 5,
		name: "Service 5",
		description: "This is a service 5",
		validTimeDuration: "5 Month",
		serviceCharge: 500,
	},
	{
		id: 6,
		name: "Service 6",
		description: "This is a service 6",
		validTimeDuration: "6 Month",
		serviceCharge: 600,
	},
	{
		id: 7,
		name: "Service 7",
		description: "This is a service 7",
		validTimeDuration: "7 Month",
		serviceCharge: 700,
	},
	{
		id: 8,
		name: "Service 8",
		description: "This is a service 8",
		validTimeDuration: "8 Month",
		serviceCharge: 800,
	},
	{
		id: 9,
		name: "Service 9",
		description: "This is a service 9",
		validTimeDuration: "9 Month",
		serviceCharge: 900,
	},
	{
		id: 10,
		name: "Service 10",
		description: "This is a service 10",
		validTimeDuration: "10 Month",
		serviceCharge: 1000,
	},
];

function Services() {
	const { isOpen, onOpen, onClose } = useDisclosure();
	const {
		isOpen: isOpenDeactivate,
		onOpen: onOpenDeactivate,
		onClose: onCloseDeactivate,
	} = useDisclosure();
	const [serviceIndex, setServiceIndex] = React.useState(0);
	const [selectedType, setSelectedType] = React.useState("all");

	const openActivatePopUp = (serviceIndex, myService = false) => {
		setServiceIndex(serviceIndex);
		myService ? setSelectedType("my") : setSelectedType("all");
		onOpen();
	};

	const openDeactivatePopUp = (serviceIndex, myService = false) => {
		setServiceIndex(serviceIndex);
		myService ? setSelectedType("my") : setSelectedType("all");
		onOpenDeactivate();
	};

	return (
		<>
			<Box mt={3}>
				<Text pl={5} as={"b"} fontSize={24}>
					My Services
				</Text>
				<ServiceList
					myService={true}
					services={myServices}
					openPopup={openDeactivatePopUp}
				/>
				<Text pl={5} as={"b"} fontSize={24}>
					Explore Services
				</Text>
				<ServiceList
					myService={false}
					services={allServices}
					openPopup={openActivatePopUp}
				/>
			</Box>
			<ActivateModal
				isOpen={isOpen}
				onClose={onClose}
				serviceDetails={
					selectedType === "my"
						? myServices[serviceIndex]
						: allServices[serviceIndex]
				}
			/>
			<DeactivateModal
				isOpen={isOpenDeactivate}
				onClose={onCloseDeactivate}
				serviceDetails={
					selectedType === "my"
						? myServices[serviceIndex]
						: allServices[serviceIndex]
				}
			/>
		</>
	);
}

export default Services;
