import React from 'react'

import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    Heading,
    Text,
    Button,
} from '@chakra-ui/react'


const ActivateModal = ({
    serviceDetails,
    isOpen,
    onClose

}) => {

    return (
        <>
            <Modal closeOnOverlayClick={false} isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>
                        <Heading size="lg" color={"#333"}>
                            {serviceDetails.name}
                        </Heading>
                    </ModalHeader>
                    <ModalCloseButton />
                    <ModalBody pb={6}>
                        <Text color={"#444"} fontSize={"12px"}>Description</Text>
                        <Text mb={1}>{serviceDetails.description}</Text>
                        <Text color={"#444"} fontSize={"12px"}>Valid Time Duration</Text>
                        <Text mb={1}>{serviceDetails.validTimeDuration}</Text>
                        <Text color={"#444"} fontSize={"12px"}>Service Charge</Text>
                        <Text>{serviceDetails.serviceCharge}</Text>
                    </ModalBody>

                    <ModalFooter gap={1}>
                        <Button onClick={onClose}>Cancel</Button>
                        <Button colorScheme='blue' mr={3}>
                            Pay & Activate
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    )
}

export default ActivateModal

