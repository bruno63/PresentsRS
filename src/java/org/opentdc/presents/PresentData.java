package org.opentdc.presents;

import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Present
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class PresentData {
	private String id;
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date date;
	private String from;
	private String to;
	// ptype is an enum that is handled on the client side. On the server, we
	// treat it as an ordinary String
	private String ptype;
	private String comment;

	public PresentData() {
		this.date = new Date();
		this.from = "from";
		this.to = "to";
		this.ptype = "ptype";
		this.comment = "comment";
	}

	public PresentData(Date date, String from, String to, String ptype,
			String comment) {
		this.date = date;
		this.from = from;
		this.to = to;
		this.ptype = ptype;
		this.comment = comment;
	}

	/**
	 * @return the ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the ptype
	 */
	public String getPtype() {
		return ptype;
	}

	/**
	 * @param ptype
	 *            the ptype to set
	 */
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder _sb = new StringBuilder();
		Formatter _formatter = new Formatter(_sb, Locale.US);
		_formatter
				.format("Present [ID:%s\nDate:%s\nfrom:%s\nto:%s\nptype:%s\ncomment:%s\n",
						getId(), getDate(), getFrom(), getTo(), getPtype(),
						getComment());
		_formatter.close();
		return _sb.toString();
	}
}