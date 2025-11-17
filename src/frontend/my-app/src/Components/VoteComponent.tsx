import { useEffect, useState } from "react";
import { Vote, VoteOption, VoteOptions } from "./Model/Poll";
import { BACKEND_URL } from "./Constants/constants.js";

type PollListItem = { id: number; title: string; creatorName: string };

export default function VotePoll({ userName }: { userName: string }) {
    const [polls, setPolls] = useState<PollListItem[]>([]);
    const [activeId, setActiveId] = useState<number | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [pollJson, setPollJson] = useState<any>(null);
    const [pollOptions, setPollOptions] = useState<VoteOptions>();
    const [votes, setVotes] = useState<number[]>([]);
    const [pollTitle, setPollTitle] = useState("");
    const [pollQuestion, setPollQuestion] = useState("");
    const url = BACKEND_URL + "/polls/" + activeId + "/votes";
    const [seeAllPolls, setSeeAllPolls] = useState(true);

    useEffect(() => {
        (async () => {
            try {
                const r = await fetch(`${BACKEND_URL}/polls/info`, { credentials: "include" });
                if (r.status === 204) return;
                if (!r.ok) throw new Error("list fetch failed");
                setPolls(await r.json());
            } catch {
                setError("Failed loading poll list");
            }
        })();
    }, []);

    useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const pollParam = params.get("poll");
    if (!pollParam) return;

    const id = Number(pollParam);
    if (Number.isNaN(id)) return;

    getPollById(id);
}, []); 
    const getVotes = async (pollId: number) => {
        const res = await fetch(BACKEND_URL + "/polls/" + pollId + "/votes/" + "results");
        const data = await res.json();
        setVotes(data);
    };

    useEffect(() => {
        if (activeId === null) {
            return;
        }

        const intervalId = setInterval(() => {
            getVotes(activeId);
        }, 3000);

        return () => clearInterval(intervalId);
    }, [activeId]);

    const getPollById = async (id: number) => {
        setError(null);
        try {
            const res = await fetch(`${BACKEND_URL}/polls/${id}`, { credentials: "include" });
            if (!res.ok) throw new Error("poll fetch failed");
            const data = await res.json();
            const vopts = new VoteOptions().fromJSON(data.options);
            setPollOptions(vopts);
            setPollJson(data);
            setPollTitle(data.title);
            setPollQuestion(data.question);
            setActiveId(data.id);
            await getVotes(id);
            setSeeAllPolls(false);
        } catch {
            setError(`Error fetching poll with id ${id}`);
        }
    };

    const vote = async (presentationOrder: number) => {
        if (activeId == null) return;
        try {
            const vote: Vote = new Vote(presentationOrder, activeId, null);
            if (userName) {
                vote.userName = userName;
            }
            const getCookieRaw = (name: string) =>
                document.cookie.split('; ')
                    .find(c => c.startsWith(name + '='))?.split('=')[1] ?? '';
            const xsrf = getCookieRaw('XSRF-TOKEN');
            console.log(vote);
            const response = await fetch(url, {
                method: 'POST',
                credentials: "include",
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                    'X-XSRF-TOKEN': xsrf,
                },
                body: JSON.stringify(vote.toJSON()),
            });
            getVotes(activeId);
        } catch (e: any) {
            setError("Error voting");
        }
    };

    return (
        <div>
            <button onClick={() => setSeeAllPolls(!seeAllPolls)}>See All Polls</button>
            {seeAllPolls && (
                <div>
                    <h3>Choose a poll</h3>
                    {polls.length === 0 && <p>No polls available.</p>}
                    <ul>
                        {polls.map(p => (
                            <li key={p.id}>
                                <button onClick={() => getPollById(p.id)}>
                                    {p.title} — {p.creatorName}
                                </button>
                            </li>
                        ))}
                    </ul>
                </div>
            )}
            {error && <h4>{error}</h4>}

            {pollJson && (
                <>
                    <h2>{pollTitle}</h2>
                    <h3>Question: {pollQuestion}</h3>
                    <div className="voteOptionsBox">
                        {pollOptions?.getVoteOptions().map((option: VoteOption) => (
                            <div className="voteOption" key={option.optionId}>
                                <span className="caption">{option.getCaption()}</span>
                                <button onClick={() => vote(option.presentationOrder)}>vote</button>
                                <span className="voteCount">{votes?.[option.presentationOrder] ?? 0}</span>
                            </div>
                        ))}
                    </div>
                </>
            )}
        </div>
    );
}
