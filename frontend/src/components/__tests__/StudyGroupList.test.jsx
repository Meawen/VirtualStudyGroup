import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen, waitFor } from '@testing-library/react';
import StudyGroupList from '../StudyGroupList.jsx';
import { getPublicGroups } from '../../api';

vi.mock('../../api', () => ({
    getPublicGroups: vi.fn(),
}));

beforeEach(() => {
    vi.clearAllMocks();
    getPublicGroups.mockResolvedValue([]);
});

describe('StudyGroupList Component', () => {
    it('displays loading message initially', () => {
        render(<StudyGroupList />);
        expect(screen.getByText('Loading groups...')).toBeTruthy();
    });

    it('renders a list of study groups when API call succeeds', async () => {
        const mockGroups = [
            { id: 1, name: 'React Group', description: 'Learn React together', createdAt: '2024-01-01T10:00:00Z' },
            { id: 2, name: 'Node.js Group', description: 'Mastering Node.js', createdAt: '2024-01-02T12:00:00Z' },
        ];
        getPublicGroups.mockResolvedValue(mockGroups);

        render(<StudyGroupList />);

        await waitFor(() => expect(getPublicGroups).toHaveBeenCalledTimes(1));

        expect(screen.getByText('React Group')).toBeTruthy();
        expect(screen.getByText('Learn React together')).toBeTruthy();
        expect(screen.getByText('Node.js Group')).toBeTruthy();
        expect(screen.getByText('Mastering Node.js')).toBeTruthy();
    });

    it('handles API failure and shows no groups', async () => {
        getPublicGroups.mockRejectedValue(new Error('Failed to fetch groups'));

        render(<StudyGroupList />);

        await waitFor(() => expect(getPublicGroups).toHaveBeenCalledTimes(1));

        expect(screen.getByText('Loading groups...')).toBeTruthy();
    });

    it('renders an empty list when API returns an unexpected format', async () => {
        getPublicGroups.mockResolvedValue(null);

        render(<StudyGroupList />);

        await waitFor(() => expect(getPublicGroups).toHaveBeenCalledTimes(1));

        expect(screen.getByText('Loading groups...')).toBeTruthy();
    });
});
