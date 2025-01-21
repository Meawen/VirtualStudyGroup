import { render, screen, fireEvent } from '@testing-library/react';
import CreateGroupForm from '../CreateGroupForm';
import { createGroup } from '../../api';




jest.mock('../../api', () => ({
    createGroup: jest.fn(),
}));

describe('CreateGroupForm Component', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });

    test('renders form elements correctly', () => {
        render(<CreateGroupForm />);

        expect(screen.getByPlaceholderText('Group Name')).toBeInTheDocument();
        expect(screen.getByPlaceholderText('Description')).toBeInTheDocument();
        expect(screen.getByRole('button', { name: /create group/i })).toBeInTheDocument();
    });

    test('updates state when input changes', () => {
        render(<CreateGroupForm />);

        const nameInput = screen.getByPlaceholderText('Group Name');
        const descriptionInput = screen.getByPlaceholderText('Description');

        fireEvent.change(nameInput, { target: { value: 'React Study Group' } });
        fireEvent.change(descriptionInput, { target: { value: 'A group to learn React' } });

        expect(nameInput.value).toBe('React Study Group');
        expect(descriptionInput.value).toBe('A group to learn React');
    });

    test('submits form and calls createGroup API', async () => {
        createGroup.mockResolvedValue({ data: { message: 'Success' } });

        render(<CreateGroupForm />);

        fireEvent.change(screen.getByPlaceholderText('Group Name'), { target: { value: 'React Study Group' } });
        fireEvent.change(screen.getByPlaceholderText('Description'), { target: { value: 'A group to learn React' } });

        fireEvent.click(screen.getByRole('button', { name: /create group/i }));

        expect(createGroup).toHaveBeenCalledWith({
            name: 'React Study Group',
            description: 'A group to learn React',
            visibility: 'PUBLIC',
        });

        await screen.findByText('Group created successfully!');
    });

    test('handles API error correctly', async () => {
        createGroup.mockRejectedValue(new Error('Failed to create group'));

        render(<CreateGroupForm />);

        fireEvent.change(screen.getByPlaceholderText('Group Name'), { target: { value: 'React Study Group' } });
        fireEvent.change(screen.getByPlaceholderText('Description'), { target: { value: 'A group to learn React' } });

        fireEvent.click(screen.getByRole('button', { name: /create group/i }));

        await screen.findByText('Failed to create group.');
    });
});
