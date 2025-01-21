import { describe, it, expect, vi } from 'vitest';
import { render, screen, fireEvent } from '@testing-library/react';
import LearningGoalForm from '../LearningGoalForm.jsx';

describe('LearningGoalForm Component', () => {
    it('renders form elements correctly', () => {
        render(<LearningGoalForm onSubmit={vi.fn()} />);

        expect(screen.getByLabelText(/Title/i)).toBeTruthy();
        expect(screen.getByLabelText(/Description/i)).toBeTruthy();
        expect(screen.getByLabelText(/Status/i)).toBeTruthy();
        expect(screen.getByLabelText(/Deadline/i)).toBeTruthy();
        expect(screen.getByRole('button', { name: /Add Goal/i })).toBeTruthy();
    });

    it('updates state when input changes', () => {
        render(<LearningGoalForm onSubmit={vi.fn()} />);

        fireEvent.change(screen.getByLabelText(/Title/i), { target: { value: 'Learn React' } });
        fireEvent.change(screen.getByLabelText(/Description/i), { target: { value: 'Study React concepts' } });
        fireEvent.change(screen.getByLabelText(/Status/i), { target: { value: 'IN PROGRESS' } });
        fireEvent.change(screen.getByLabelText(/Deadline/i), { target: { value: '2025-01-01T10:00' } });

        expect(screen.getByLabelText(/Title/i).value).toBe('Learn React');
        expect(screen.getByLabelText(/Description/i).value).toBe('Study React concepts');
        expect(screen.getByLabelText(/Status/i).value).toBe('IN PROGRESS');
        expect(screen.getByLabelText(/Deadline/i).value).toBe('2025-01-01T10:00');
    });

    it('calls onSubmit with correct data on form submission', () => {
        const mockSubmit = vi.fn();

        render(<LearningGoalForm onSubmit={mockSubmit} />);

        fireEvent.change(screen.getByLabelText(/Title/i), { target: { value: 'Learn Testing' } });
        fireEvent.change(screen.getByLabelText(/Description/i), { target: { value: 'Improve testing skills' } });
        fireEvent.change(screen.getByLabelText(/Status/i), { target: { value: 'DONE' } });
        fireEvent.change(screen.getByLabelText(/Deadline/i), { target: { value: '2025-06-01T15:30' } });

        fireEvent.click(screen.getByRole('button', { name: /Add Goal/i }));

        expect(mockSubmit).toHaveBeenCalledWith({
            title: 'Learn Testing',
            description: 'Improve testing skills',
            status: 'DONE',
            deadline: '2025-06-01T15:30',
        });
    });

    it('clears form fields after submission', () => {
        const mockSubmit = vi.fn();

        render(<LearningGoalForm onSubmit={mockSubmit} />);

        fireEvent.change(screen.getByLabelText(/Title/i), { target: { value: 'Clear Form' } });
        fireEvent.change(screen.getByLabelText(/Description/i), { target: { value: 'Test clear after submit' } });
        fireEvent.click(screen.getByRole('button', { name: /Add Goal/i }));

        expect(screen.getByLabelText(/Title/i).value).toBe('');
        expect(screen.getByLabelText(/Description/i).value).toBe('');
        expect(screen.getByLabelText(/Status/i).value).toBe('TO DO');
        expect(screen.getByLabelText(/Deadline/i).value).toBe('');
    });
});
