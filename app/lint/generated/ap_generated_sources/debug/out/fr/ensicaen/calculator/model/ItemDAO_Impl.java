package fr.ensicaen.calculator.model;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ItemDAO_Impl implements ItemDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Item> __insertionAdapterOfItem;

  private final EntityDeletionOrUpdateAdapter<Item> __deletionAdapterOfItem;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public ItemDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfItem = new EntityInsertionAdapter<Item>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `Item` (`_id`,`_title`,`_description`,`_date`,`_image`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Item entity) {
        statement.bindLong(1, entity._id);
        if (entity._title == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity._title);
        }
        if (entity._description == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity._description);
        }
        if (entity._date == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity._date);
        }
        if (entity._image == null) {
          statement.bindNull(5);
        } else {
          statement.bindBlob(5, entity._image);
        }
      }
    };
    this.__deletionAdapterOfItem = new EntityDeletionOrUpdateAdapter<Item>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `Item` WHERE `_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Item entity) {
        statement.bindLong(1, entity._id);
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM item";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Item item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfItem.insert(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Item item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfItem.handle(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDelete.release(_stmt);
    }
  }

  @Override
  public List<Item> getAll() {
    final String _sql = "SELECT * FROM item";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "_id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "_title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "_description");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "_date");
      final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "_image");
      final List<Item> _result = new ArrayList<Item>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Item _item;
        final String _tmp_title;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmp_title = null;
        } else {
          _tmp_title = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmp_description;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmp_description = null;
        } else {
          _tmp_description = _cursor.getString(_cursorIndexOfDescription);
        }
        final String _tmp_date;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmp_date = null;
        } else {
          _tmp_date = _cursor.getString(_cursorIndexOfDate);
        }
        final byte[] _tmp_image;
        if (_cursor.isNull(_cursorIndexOfImage)) {
          _tmp_image = null;
        } else {
          _tmp_image = _cursor.getBlob(_cursorIndexOfImage);
        }
        _item = new Item(_tmp_title,_tmp_description,_tmp_date,_tmp_image);
        _item._id = _cursor.getInt(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
