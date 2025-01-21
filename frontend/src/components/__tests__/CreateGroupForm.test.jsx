import { describe, it, expect, beforeEach, vi } from 'vitest';
import { render, screen, fireEvent, act } from '@testing-library/react';
import CreateGroupForm from '../CreateGroupForm.jsx';


vi.mock('../../api', () => ({
    createGroup: vi.fn(),
}));


beforeAll(() => {
    global.alert = vi.fn();
});

describe('CreateGroupForm Component', () => {
    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('renders form elements correctly', () => {
        render(<CreateGroupForm />);
        expect(screen.getByPlaceholderText('Group Name')).toBeTruthy();
        expect(screen.getByPlaceholderText('Description')).toBeTruthy();
        expect(screen.getByRole('button', { name: /create group/i })).toBeTruthy();
    });

    it('updates state when input changes', async () => {
        render(<CreateGroupForm />);

        const nameInput = screen.getByPlaceholderText('Group Name');
        const descriptionInput = screen.getByPlaceholderText('Description');

        await act(async () => {
            fireEvent.input(nameInput, { target: { value: 'React Study Group' } });
            fireEvent.input(descriptionInput, { target: { value: 'A group to learn React' } });
        });

        expect(nameInput.value).toBe('React Study Group');
        expect(descriptionInput.value).toBe('A group to learn React');
    });

    it('submits the form and calls createGroup API successfully', async () => {
        const { createGroup } = await import('../../api');
        createGroup.mockResolvedValue({});

        render(<CreateGroupForm />);

        await act(async () => {
            fireEvent.change(screen.getByPlaceholderText('Group Name'), { target: { value: 'React Study Group' } });
            fireEvent.change(screen.getByPlaceholderText('Description'), { target: { value: 'A group to learn React' } });
            fireEvent.click(screen.getByRole('button', { name: /create group/i }));
        });

        expect(createGroup).toHaveBeenCalledWith({
            name: 'React Study Group',
            description: 'A group to learn React',
            visibility: 'PUBLIC',
        });

        expect(global.alert).toHaveBeenCalledWith('Group created successfully!');
    });

    it('handles API error correctly', async () => {
        const { createGroup } = await import('../../api');
        createGroup.mockRejectedValue(new Error('Failed to create group'));

        render(<CreateGroupForm />);

        await act(async () => {
            fireEvent.change(screen.getByPlaceholderText('Group Name'), { target: { value: 'React Study Group' } });
            fireEvent.change(screen.getByPlaceholderText('Description'), { target: { value: 'A group to learn React' } });
            fireEvent.click(screen.getByRole('button', { name: /create group/i }));
        });

        expect(global.alert).toHaveBeenCalledWith('Failed to create group.');
    });
});
