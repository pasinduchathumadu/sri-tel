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


const DeactivateModal = ({
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
                        <Text mb={1}>
                            Are you sure you want to deactivate this service?
                        </Text>
                    </ModalBody>

                    <ModalFooter gap={1}>
                        <Button onClick={onClose}>Cancel</Button>
                        <Button colorScheme='blue' mr={3}>
                            Confirm
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    )
}

export default DeactivateModal

