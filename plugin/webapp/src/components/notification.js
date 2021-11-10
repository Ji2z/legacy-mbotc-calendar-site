/* eslint-disable */
import React from 'react';

import {NotificationContainer} from 'react-notifications';
import 'react-notifications/lib/notifications.css';

class NotificationAlert extends React.Component {
    render() {
      return (
        <div>
          <NotificationContainer/>
        </div>
      );
    }
}

export default NotificationAlert;
