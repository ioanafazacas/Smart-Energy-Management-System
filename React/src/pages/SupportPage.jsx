import { useAuth } from '../context/AuthContext';
import ChatWidget from '../components/chat/ChatWidget';

const SupportPage = () => {
  const { user } = useAuth();

  if (!user) {
    return <p>Loading...</p>;
  }

  return (
    <div>
      <h2>Customer Support</h2>
      <ChatWidget user={user} />
    </div>
  );
};

export default SupportPage;
